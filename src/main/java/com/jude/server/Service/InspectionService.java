package com.jude.server.Service;

import com.jude.server.DTO.Enum.EmergencyEquipmentType;
import com.jude.server.DTO.Enum.InspectionType;
import com.jude.server.DTO.Enum.RoomType;

import com.jude.server.DTO.Res.Detail.Inspection.GenInspectionDetailDTO;
import com.jude.server.DTO.Res.Detail.Inspection.InspectionDetailResDTO;
import com.jude.server.DTO.Res.Detail.Inspection.InspectionInfoDetailDTO;
import com.jude.server.DTO.Res.Detail.Inspection.InspectionUpdateDTO;
import com.jude.server.DTO.Res.Detail.Inspection.InspectionUpdateRequestDTO;
import com.jude.server.DTO.Res.Detail.Inspection.RoomInspectionDetailDTO;
import com.jude.server.DTO.Res.Detail.Inspection.UPSInspectionDetailDTO;
import com.jude.server.DTO.Res.Pagenation.Inspection.GenInspectionDTO;
import com.jude.server.DTO.Res.Pagenation.Inspection.InspectionInfoDTO;
import com.jude.server.DTO.Res.Pagenation.Inspection.InspectionPageResDTO;
import com.jude.server.DTO.Res.Pagenation.Inspection.RoomInspectionDTO;
import com.jude.server.DTO.Res.Pagenation.Inspection.UPSInspectionDTO;
import com.jude.server.DTO.Res.Pagenation.PageResponse;
import com.jude.server.Entity.Equipment.BAT;
import com.jude.server.Entity.Equipment.Gen;
import com.jude.server.Entity.Equipment.UPS;
import com.jude.server.Entity.Facility;
import com.jude.server.Entity.Inspection.Inspection;
import com.jude.server.Entity.PowerReceiving;
import com.jude.server.Entity.Room;
import com.jude.server.Repository.BATRepository;
import com.jude.server.Repository.FacilityRepository;
import com.jude.server.Repository.GenRepository;
import com.jude.server.Repository.InspectionRepository;
import com.jude.server.Repository.PowerReceivingRepository;
import com.jude.server.Repository.RoomRepository;
import com.jude.server.Repository.UPSRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InspectionService {

    private final FacilityRepository facilityRepository;
    private final RoomRepository roomRepository;
    private final PowerReceivingRepository powerReceivingRepository;
    private final GenRepository genRepository;
    private final UPSRepository upsRepository;
    private final InspectionRepository inspectionRepository;
    private final BATRepository batRepository;

    @Transactional(readOnly = true)
    public PageResponse<InspectionPageResDTO> inspectionPage(
        Pageable pageable
    ) {
        /*
            1. Facility 페이지 조회
            2. facilityIds 추출
            3. facilityIds로 Room 일괄 조회 (Room 12 → Facility 1)
            4. roomIds 추출
            5. roomIds로 PowerReceiving 일괄 조회 (Room11 → PR 101)
            6. roomIds로 Gen 일괄 조회 (Gen 1 → Room 11, 150kVA)
            7. roomIds로 UPS 일괄 조회 (UPS 1 → Room 11, 70kVA)
            8. facilityIds로 Inspection 일괄 조회 (Facility 1 → 정기검사, 2025-01-01)
            9. 조회한 데이터를 기준별 Map으로 변환
                PR          → Map<roomId, PowerReceiving> (1:1 -> toMap)
                Gen         → Map<roomId, List<Gen>> (1:N -> groupingBy)
                UPS         → Map<roomId, List<UPS>> (1:N -> groupingBy)
                Inspection  → Map<facilityId, List<Inspection>>
                Room        → Map<facilityId, List<Room>>
            10. Facility별 검사 대상 여부 판정
            11. 조건에 맞는 Facility만 DTO로 조립
            12. PageResponse로 변환
        */

        Page<Facility> facilityPage = facilityRepository.findAll(pageable);
        List<Facility> facilities = facilityPage.getContent();

        List<Long> facilityIds = facilities.stream()
            .map(Facility::getFacilityId)
            .toList();

        List<Room> rooms = roomRepository.findByFacilityFacilityIdIn(facilityIds);
        List<Long> roomIds = rooms.stream()
            .map(Room::getRoomId)
            .toList();

        List<PowerReceiving> prs = powerReceivingRepository.findByRoomRoomIdIn(roomIds);
        List<Gen> gens = genRepository.findByRoomIds(roomIds);
        List<UPS> ups = upsRepository.findByRoomIds(roomIds);

        List<Inspection> inspections = inspectionRepository.findByFacilityFacilityIdIn(facilityIds);
        List<BAT> bats = roomIds.isEmpty()
            ? List.of()
            : batRepository.findByRoomIds(roomIds);

        Map<Long, PowerReceiving> prMap = prs.stream().collect(Collectors.toMap(
            pr -> pr.getRoom().getRoomId(),
            pr -> pr
        ));

        Map<Long, List<Gen>> genMap = gens.stream().collect(Collectors.groupingBy(
            gen -> gen.getEmergencyEquipment().getRoom().getRoomId()
        ));

        Map<Long, List<UPS>> upsMap = ups.stream().collect(Collectors.groupingBy(
            u -> u.getEmergencyEquipment().getRoom().getRoomId()
        ));

        Map<Long, List<Room>> roomMap = rooms.stream().collect(Collectors.groupingBy(
            room -> room.getFacility().getFacilityId()
        ));

        Map<Long, List<Inspection>> inspectionMap = inspections.stream().collect(Collectors.groupingBy(
            inspection -> inspection.getFacility().getFacilityId()
        ));

        Map<Long, List<BAT>> batMap =
            bats.stream()
                .collect(Collectors.groupingBy(
                    bat -> bat.getEmergencyEquipment()
                        .getRoom()
                        .getRoomId()
                ));

        List<InspectionPageResDTO> content =
            facilities.stream()
                .map(facility -> {
                    Long facilityId = facility.getFacilityId();

                    List<Room> facilityRooms = roomMap.getOrDefault(
                        facilityId,
                        List.of()
                    );

                    List<PowerReceiving> facilityPrs =
                        facilityRooms.stream()
                            .map(room -> prMap.get(room.getRoomId()))
                            .filter(Objects::nonNull)
                            .toList();

                    List<Gen> facilityGens =
                        facilityRooms.stream().flatMap(room ->
                            genMap.getOrDefault(
                                room.getRoomId(),
                                List.of()
                            ).stream()
                        ).toList();

                    List<UPS> facilityUps =
                        facilityRooms.stream()
                            .flatMap(room ->
                                upsMap.getOrDefault(
                                    room.getRoomId(),
                                    List.of()
                                ).stream()
                            )
                            .toList();

                    List<Inspection> facilityInspections =
                        inspectionMap.getOrDefault(
                            facilityId,
                            List.of()
                        );

                    PowerReceiving representativePr =
                        facilityPrs.stream()
                            .findFirst()
                            .orElse(null);

                    Room representativeRoom =
                        facilityRooms.stream()
                            .findFirst()
                            .orElse(null);

                    RoomInspectionDTO roomDTO =
                        RoomInspectionDTO.toRes(
                            representativeRoom,
                            representativePr
                        );

                    List<GenInspectionDTO> genDTOs =
                        facilityGens.stream()
                            .map(GenInspectionDTO::toRes)
                            .toList();

                    List<UPSInspectionDTO> upsDTOs =
                        facilityUps.stream()
                            .map(UPSInspectionDTO::toRes)
                            .toList();

                    List<BAT> facilityBats =
                        facilityRooms.stream()
                            .flatMap(room ->
                                batMap.getOrDefault(
                                    room.getRoomId(),
                                    List.of()
                                ).stream()
                            )
                            .toList();

                    List<BAT> facilityUpsBats =
                        facilityBats.stream()
                            .filter(bat ->
                                bat.getEmergencyEquipment()
                                    .getEmergencyEquipmentType()
                                    == EmergencyEquipmentType.UPS
                            )
                            .toList();

                    Map<InspectionType, Inspection>
                        latestInspectionMap =
                        facilityInspections.stream()
                            .filter(inspection ->
                                inspection.getCompleteDate()
                                    != null
                            )
                            .collect(Collectors.toMap(
                                Inspection::getInspectionType,
                                inspection -> inspection,
                                (existing, replacement) ->
                                    existing.getCompleteDate()
                                        .isAfter(
                                            replacement
                                                .getCompleteDate()
                                        )
                                        ? existing
                                        : replacement
                            ));

                    /*
                     * 9. 검사 대상 여부 판정
                     *
                     * 저압: 1000V 이하
                     * 고압: 1000V 초과
                     *
                     * 정기검사:
                     * - 1000V 이하이고 계약전력 75kW 이상
                     * - 또는 1000V 초과
                     */
                    boolean regularRequired =
                        facilityPrs.stream()
                            .anyMatch(pr ->
                                pr.getVoltageV() > 1000
                                    || (
                                    pr.getVoltageV() <= 1000
                                        && pr.getContractKw()
                                        >= 75
                                )
                            );

                    /*
                     * UPS검사:
                     * UPS 중 하나라도 70kVA 이상
                     */
                    boolean upsRequired =
                        facilityUpsBats.stream()
                            .anyMatch(bat ->
                                bat.getTotalCapaKwh() != null
                                    && bat.getTotalCapaKwh() >= 70
                            );

                    /*
                     * 발전기검사:
                     * 발전기가 하나라도 있으면 대상
                     */
                    boolean generatorRequired =
                        !facilityGens.isEmpty();

                    /*
                     * 검사 응답 DTO 목록
                     */
                    List<InspectionInfoDTO> inspectionDTOs =
                        new ArrayList<>();

                    /*
                     * 정기검사 대상
                     */
                    if (regularRequired) {
                        inspectionDTOs.add(
                            InspectionInfoDTO.toRes(
                                InspectionType.정기검사,
                                latestInspectionMap.get(
                                    InspectionType.정기검사
                                )
                            )
                        );
                    }




                    /*
                     * 안전진단은 아직 별도의 대상 조건이 없으므로
                     * 기존 검사 이력이 존재하는 경우 표시
                     */
                    if (latestInspectionMap.containsKey(
                        InspectionType.안전진단
                    )) {
                        inspectionDTOs.add(
                            InspectionInfoDTO.toRes(
                                InspectionType.안전진단,
                                latestInspectionMap.get(
                                    InspectionType.안전진단
                                )
                            )
                        );
                    }

                    /*
                     * UPS검사 대상
                     */
                    if (upsRequired) {
                        inspectionDTOs.add(
                            InspectionInfoDTO.toRes(
                                InspectionType.UPS검사,
                                latestInspectionMap.get(
                                    InspectionType.UPS검사
                                )
                            )
                        );
                    }

                    /*
                     * 발전기검사 대상
                     */
                    if (generatorRequired) {
                        inspectionDTOs.add(
                            InspectionInfoDTO.toRes(
                                InspectionType.발전기검사,
                                latestInspectionMap.get(
                                    InspectionType.발전기검사
                                )
                            )
                        );
                    }

                    /*
                     * 설비사용전검사는 기존 이력이 있는 경우 표시
                     */
                    if (latestInspectionMap.containsKey(
                        InspectionType.설비사용전검사
                    )) {
                        inspectionDTOs.add(
                            InspectionInfoDTO.toRes(
                                InspectionType.설비사용전검사,
                                latestInspectionMap.get(
                                    InspectionType.설비사용전검사
                                )
                            )
                        );
                    }

                    /*
                     * 발전기사용전검사는 기존 이력이 있는 경우 표시
                     */
                    if (latestInspectionMap.containsKey(
                        InspectionType.발전기사용전검사
                    )) {
                        inspectionDTOs.add(
                            InspectionInfoDTO.toRes(
                                InspectionType.발전기사용전검사,
                                latestInspectionMap.get(
                                    InspectionType.발전기사용전검사
                                )
                            )
                        );
                    }

                    /*
                     * 어떠한 검사에도 해당하지 않는 Facility는 제외
                     */
                    if (inspectionDTOs.isEmpty()) {
                        return null;
                    }

                    /*
                     * 10. 최종 DTO 조립
                     */
                    return InspectionPageResDTO.toRes(
                        facility,
                        roomDTO,
                        genDTOs,
                        upsDTOs,
                        inspectionDTOs
                    );
                })
                .filter(Objects::nonNull)
                .toList();

        /*
         * 11. Page 객체 생성
         */
        Page<InspectionPageResDTO> resultPage =
            new PageImpl<>(
                content,
                pageable,
                facilityPage.getTotalElements()
            );

        /*
         * 12. PageResponse 변환
         */
        return PageResponse.toPageResponse(resultPage);
    }

    @Transactional(readOnly = true)
    public InspectionDetailResDTO detail(
        Long facilityId
    ) {

        /*
         * 1. Facility 조회
         */
        Facility facility =
            facilityRepository.findById(facilityId)
                .orElseThrow(
                    () -> new RuntimeException(
                        "Facility not found: " + facilityId
                    )
                );

        /*
         * 2. 주변전실 조회
         */
        Room room =
            roomRepository
                .findByFacilityIdAndRoomType(
                    facilityId,
                    RoomType.주변전실
                )
                .stream()
                .findFirst()
                .orElse(null);

        /*
         * 3. 주변전실의 수전설비 조회
         */
        PowerReceiving powerReceiving = null;

        if (room != null) {
            powerReceiving =
                powerReceivingRepository
                    .findByRoomId(
                        room.getRoomId()
                    )
                    .orElse(null);
        }

        /*
         * 4. Facility의 발전기 전체 조회
         *
         * 발전기 검사 대상 여부 판단에도 사용
         */
        List<Gen> gens =
            genRepository
                .findAllByFacilityIdForDetail(
                    facilityId
                );

        /*
         * 5. Facility의 UPS 전체 조회
         */
        List<UPS> upsList =
            upsRepository
                .findAllByFacilityIdForDetail(
                    facilityId
                );

        /*
         * 6. UPS의 EmergencyEquipment ID 추출
         *
         * UPS BAT만 가져오기 위해 필요
         */
        List<Long> upsEmergencyEquipmentIds =
            upsList.stream()
                .map(ups ->
                    ups.getEmergencyEquipment()
                        .getEmergencyEquipmentId()
                )
                .distinct()
                .toList();

        /*
         * 7. UPS에 연결된 배터리 조회
         *
         * Generator BAT / Rectifier BAT는 포함하지 않음.
         */
        List<BAT> upsBatteries =
            upsEmergencyEquipmentIds.isEmpty()
                ? List.of()
                : batRepository
                    .findByEmergencyEquipmentIds(
                        upsEmergencyEquipmentIds
                    );

        /*
         * 8. Room DTO
         */
        RoomInspectionDetailDTO roomDTO =
            RoomInspectionDetailDTO.toRes(
                room,
                powerReceiving
            );

        /*
         * 9. Generator DTO
         */
        List<GenInspectionDetailDTO> genDTOs =
            gens.stream()
                .map(
                    GenInspectionDetailDTO::toRes
                )
                .toList();

        /*
         * 10. UPS DTO
         */
        List<UPSInspectionDetailDTO> upsDTOs =
            upsList.stream()
                .map(
                    UPSInspectionDetailDTO::toRes
                )
                .toList();

        /*
         * 11. 검사 대상 및 최신 검사정보 생성
         */
        List<InspectionInfoDetailDTO> inspectionDTOs =
            createInspectionDTOs(
                facilityId,
                gens,
                upsBatteries
            );

        /*
         * 12. 최종 응답
         */
        return InspectionDetailResDTO.toRes(
            facility,
            roomDTO,
            genDTOs,
            upsDTOs,
            inspectionDTOs
        );
    }


    /*
     * 검사 항목 생성
     *
     * 정기검사:
     * 무조건 표시
     *
     * 발전기검사:
     * 발전기가 하나라도 존재하면 표시
     *
     * UPS검사:
     * UPS에 연결된 BAT 중
     * totalCapaKwh >= 70 이 하나라도 있으면 표시
     */
    private List<InspectionInfoDetailDTO>
    createInspectionDTOs(
        Long facilityId,
        List<Gen> gens,
        List<BAT> upsBatteries
    ) {

        /*
         * Facility의 검사 이력 전체 조회
         */
        List<Inspection> inspections =
            inspectionRepository
                .findAllByFacilityIdForDetail(
                    facilityId
                );

        List<InspectionInfoDetailDTO> result =
            new ArrayList<>();


        /*
         * 1. 정기검사
         *
         * 항상 대상
         */
        Inspection regularInspection =
            findLatestInspection(
                inspections,
                InspectionType.정기검사
            );

        result.add(
            InspectionInfoDetailDTO.toRes(
                InspectionType.정기검사,
                regularInspection
            )
        );


        /*
         * 2. 발전기검사
         *
         * 발전기가 있을 때만 대상
         */
        boolean generatorRequired =
            !gens.isEmpty();

        if (generatorRequired) {

            Inspection generatorInspection =
                findLatestInspection(
                    inspections,
                    InspectionType.발전기검사
                );

            result.add(
                InspectionInfoDetailDTO.toRes(
                    InspectionType.발전기검사,
                    generatorInspection
                )
            );
        }


        /*
         * 3. UPS검사
         *
         * UPS에 연결된 BAT 중
         * totalCapaKwh >= 70 인 것이
         * 하나라도 있을 경우 대상
         */
        boolean upsRequired =
            upsBatteries.stream()
                .anyMatch(
                    bat ->
                        bat.getTotalCapaKwh() != null
                            && bat.getTotalCapaKwh() >= 70
                );

        if (upsRequired) {

            Inspection upsInspection =
                findLatestInspection(
                    inspections,
                    InspectionType.UPS검사
                );

            result.add(
                InspectionInfoDetailDTO.toRes(
                    InspectionType.UPS검사,
                    upsInspection
                )
            );
        }


        return result;
    }


    /*
     * 동일 InspectionType 이력이 여러 개 있을 경우
     * completeDate가 가장 최근인 검사 반환
     */
    private Inspection findLatestInspection(
        List<Inspection> inspections,
        InspectionType inspectionType
    ) {

        return inspections.stream()
            .filter(
                inspection ->
                    inspection.getInspectionType()
                        == inspectionType
            )
            .max(
                Comparator.comparing(
                    Inspection::getCompleteDate,
                    Comparator.nullsFirst(
                        Comparator.naturalOrder()
                    )
                )
            )
            .orElse(null);
    }

    @Transactional
    public void update(
        Long facilityId,
        InspectionUpdateRequestDTO request
    ) {

        /*
         * 1. Facility 조회
         */
        Facility facility =
            facilityRepository.findById(
                    facilityId
                )
                .orElseThrow(
                    () -> new RuntimeException(
                        "Facility not found: "
                            + facilityId
                    )
                );

        /*
         * 2. 수정할 검사 데이터 없으면 종료
         */
        if (
            request == null
                || request.getInspections() == null
        ) {
            return;
        }

        /*
         * 3. 검사별 수정
         */
        for (
            InspectionUpdateDTO inspectionDTO
            : request.getInspections()
        ) {

            InspectionType inspectionType =
                inspectionDTO.getInspectionType();

            if (inspectionType == null) {
                throw new IllegalArgumentException(
                    "inspectionType은 필수입니다."
                );
            }

            /*
             * 기존 검사 조회
             */
            Inspection inspection =
                inspectionRepository
                    .findForUpdate(
                        facilityId,
                        inspectionType
                    )
                    .orElse(null);

            /*
             * 기존 row가 없는 경우 INSERT
             */
            if (inspection == null) {

                Inspection newInspection =
                    Inspection.builder()
                        .facility(
                            facility
                        )
                        .inspectionType(
                            inspectionType
                        )
                        .completeDate(
                            inspectionDTO
                                .getCompleteDate()
                        )
                        .build();

                inspectionRepository.save(
                    newInspection
                );

                continue;
            }

            /*
             * 기존 row가 있는 경우 UPDATE
             */
            inspectionDTO.updateEntity(
                inspection
            );
        }
    }
}