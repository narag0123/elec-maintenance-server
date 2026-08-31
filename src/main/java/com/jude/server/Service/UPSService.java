package com.jude.server.Service;

import com.jude.server.DTO.Req.Create.EmergencyEquipmentSubDTOs.UPSCreateReqDTO;
import com.jude.server.DTO.Res.Detail.UPS.UPSBatteryDetailDTO;
import com.jude.server.DTO.Res.Detail.UPS.UPSBatteryUpdateDTO;
import com.jude.server.DTO.Res.Detail.UPS.UPSDetailDTO;
import com.jude.server.DTO.Res.Detail.UPS.UPSDetailResDTO;
import com.jude.server.DTO.Res.Detail.UPS.UPSUpdateDTO;
import com.jude.server.DTO.Res.Detail.UPS.UPSUpdateReqDTO;
import com.jude.server.DTO.Res.Pagenation.PageResponse;
import com.jude.server.DTO.Res.Pagenation.Ups.UPSBatteryDTO;
import com.jude.server.DTO.Res.Pagenation.Ups.UPSInfoDTO;
import com.jude.server.DTO.Res.Pagenation.Ups.UPSPageResDTO;
import com.jude.server.Entity.Equipment.BAT;
import com.jude.server.Entity.Equipment.EmergencyEquipment;
import com.jude.server.Entity.Equipment.UPS;
import com.jude.server.Entity.Facility;
import com.jude.server.Repository.BATRepository;
import com.jude.server.Repository.FacilityRepository;
import com.jude.server.Repository.RoomRepository;
import com.jude.server.Repository.UPSRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UPSService {

    private final UPSRepository upsRepository;
    private final BATRepository batRepository;
    private final FacilityRepository facilityRepository;
    private final RoomRepository roomRepository;


    public void upsCreateReq(
        UPSCreateReqDTO dto,
        EmergencyEquipment emergencyEquipment
    ){
        UPS ups = UPSCreateReqDTO.toEntity(dto, emergencyEquipment);
        upsRepository.save(ups);
    }



    @Transactional(readOnly = true)
    public PageResponse<UPSPageResDTO> page(
        Pageable pageable
    ) {

        /*
         * 1. UPS가 존재하는 Facility만 페이지 조회
         */
        Page<Facility> facilityPage =
            facilityRepository.findAllHasUPS(pageable);

        List<Facility> facilities =
            facilityPage.getContent();

        /*
         * 2. FacilityId 추출
         */
        List<Long> facilityIds =
            facilities.stream()
                .map(Facility::getFacilityId)
                .toList();

        if (facilityIds.isEmpty()) {
            return PageResponse.toPageResponse(
                new PageImpl<>(
                    List.of(),
                    pageable,
                    facilityPage.getTotalElements()
                )
            );
        }

        /*
         * 3. Facility들의 UPS 조회
         */
        List<UPS> upsList =
            upsRepository.findByFacilityIds(facilityIds);

        /*
         * 4. EmergencyEquipmentId 추출
         */
        List<Long> emergencyEquipmentIds =
            upsList.stream()
                .map(ups ->
                    ups.getEmergencyEquipment()
                        .getEmergencyEquipmentId()
                )
                .toList();

        /*
         * 5. BAT 조회
         */
        List<BAT> batteries =
            emergencyEquipmentIds.isEmpty()
                ? List.of()
                : batRepository.findByEmergencyEquipmentIds(
                    emergencyEquipmentIds
                );

        /*
         * 6. Facility 기준 UPS 그룹핑
         */
        Map<Long, List<UPS>> facilityUpsMap =
            upsList.stream()
                .collect(Collectors.groupingBy(
                    ups ->
                        ups.getEmergencyEquipment()
                            .getRoom()
                            .getFacility()
                            .getFacilityId()
                ));

        /*
         * 7. EmergencyEquipment 기준 BAT 그룹핑
         */
        Map<Long, List<BAT>> batteryMap =
            batteries.stream()
                .collect(Collectors.groupingBy(
                    bat ->
                        bat.getEmergencyEquipment()
                            .getEmergencyEquipmentId()
                ));

        /*
         * 8. Facility별 DTO 생성
         */
        List<UPSPageResDTO> content =
            facilities.stream()
                .map(facility -> {

                    List<UPSInfoDTO> upsDTOs =
                        facilityUpsMap.getOrDefault(
                                facility.getFacilityId(),
                                List.of()
                            )
                            .stream()
                            .map(ups -> {

                                List<UPSBatteryDTO> batteryDTOs =
                                    batteryMap.getOrDefault(
                                            ups.getEmergencyEquipment()
                                                .getEmergencyEquipmentId(),
                                            List.of()
                                        )
                                        .stream()
                                        .map(UPSBatteryDTO::toRes)
                                        .toList();

                                return UPSInfoDTO.toRes(
                                    ups,
                                    batteryDTOs
                                );
                            })
                            .toList();

                    return UPSPageResDTO.toRes(
                        facility,
                        upsDTOs
                    );
                })
                .toList();

        /*
         * 9. Page 생성
         */
        Page<UPSPageResDTO> result =
            new PageImpl<>(
                content,
                pageable,
                facilityPage.getTotalElements()
            );

        return PageResponse.toPageResponse(result);
    }

    @Transactional(readOnly = true)
    public UPSDetailResDTO detail(
        Long facilityId
    ) {
        /*
         * 1. Facility 조회
         */
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("Facility not found: " + facilityId));

        /*
         * 2. Facility의 UPS 조회
         */
        List<UPS> upsList = upsRepository.findAllByFacilityIdForDetail(facilityId);

        /*
         * UPS가 없는 경우
         */
        if (upsList.isEmpty()) {
            return UPSDetailResDTO.toRes(
                facility,
                List.of()
            );
        }

        /*
         * 3. EmergencyEquipment ID 추출
         */

        List<Long> emergencyEquipmentIds =
            upsList.stream()
                .map(ups -> ups.getEmergencyEquipment().getEmergencyEquipmentId())
                .distinct()
                .toList();

        /*
         * 4. BAT 일괄 조회
         */

        List<BAT> batteries =
            batRepository.findByEmergencyEquipmentIds(emergencyEquipmentIds);

        /*
         * 5. EE ID 기준 BAT 그룹핑
         */
        Map<Long, List<BAT>> batteryMap =
            batteries.stream()
                .collect(
                    Collectors.groupingBy(
                        bat ->
                            bat.getEmergencyEquipment()
                                .getEmergencyEquipmentId()));

        /*
         * 6. UPS DTO 조립
         */

        List<UPSDetailDTO> upsDTOs =
            upsList.stream()
                .map(ups -> {
                    Long emergencyEquipmentId =
                        ups.getEmergencyEquipment()
                            .getEmergencyEquipmentId();

                    List<UPSBatteryDetailDTO> batteryDTOs =
                        batteryMap
                            .getOrDefault(
                                emergencyEquipmentId,
                                List.of()
                            )
                            .stream()
                            .map(UPSBatteryDetailDTO::toRes)
                            .toList();

                    return UPSDetailDTO.toRes(
                        ups,
                        batteryDTOs
                    );
                })
                .toList();

        /*
         * 7. 최종 응답
         */

        return UPSDetailResDTO.toRes(
            facility,
            upsDTOs
        );

    }



    @Transactional
    public void update(
        Long facilityId,
        UPSUpdateReqDTO request
    ) {
        /*
         * 1. Facility 존재 확인
         */

        if (!facilityRepository.existsById(
            facilityId
        )) {
            throw new RuntimeException("Facility not found: " + facilityId);
        }

        /*
         * 수정할 UPS가 없으면 종료
         */

        if (request == null || request.getUps() == null) {
            return;
        }

        /*
         * 2. UPS별 수정
         */
        for (
            UPSUpdateDTO upsDTO : request.getUps()
        ) {
            /*
             * facilityId
             * emergencyEquipmentId
             * upsId
             *
             * 관계가 모두 일치하는 UPS 조회
             */

            UPS ups = upsRepository.findForUpdate(
                        upsDTO.getUpsId(),
                        upsDTO.getEmergencyEquipmentId(),
                        facilityId
                    )
                    .orElseThrow(() -> new RuntimeException("UPS not found. "
                                    + "facilityId="
                                    + facilityId
                                    + ", emergencyEquipmentId="
                                    + upsDTO
                                    .getEmergencyEquipmentId()
                                    + ", upsId="
                                    + upsDTO.getUpsId()
                            )
                    );

            /*
             * 3. EmergencyEquipment 수정
             */
            upsDTO.updateEmergencyEquipment(ups.getEmergencyEquipment());
            /*
             * 4. UPS 수정
             */
            upsDTO.updateUPS(ups);

            /*
             * 배터리 요청이 없으면
             * 다음 UPS로
             */

            if (upsDTO.getBatteries() == null) {
                continue;
            }

            /*
             * 5. BAT 수정
             */
            for (
                UPSBatteryUpdateDTO batteryDTO : upsDTO.getBatteries()
            ) {
                BAT battery =
                    batRepository.findForUPSUpdate(
                            batteryDTO.getBatId(),
                            upsDTO.getEmergencyEquipmentId()
                        )
                        .orElseThrow(
                            () -> new RuntimeException("Battery not found. "
                                        + "batId="
                                        + batteryDTO.getBatId()
                                        + ", emergencyEquipmentId="
                                        + upsDTO
                                        .getEmergencyEquipmentId())
                        );

                batteryDTO.updateEntity(battery);}
        }
    }
}
