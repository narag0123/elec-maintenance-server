package com.jude.server.Service;

import com.jude.server.DTO.Req.Create.PowerReceivingCreateReqDTO;
import com.jude.server.DTO.Res.Create.PowerReceivingCreateResDTO;
import com.jude.server.DTO.Res.Detail.PowerReceiving.GenPowerReceivingDetailDTO;
import com.jude.server.DTO.Res.Detail.PowerReceiving.PowerReceivingDetailDTO;
import com.jude.server.DTO.Res.Detail.PowerReceiving.PowerReceivingDetailResDTO;
import com.jude.server.DTO.Res.Detail.PowerReceiving.PowerReceivingRoomUpdateDTO;
import com.jude.server.DTO.Res.Detail.PowerReceiving.PowerReceivingUpdateReqDTO;
import com.jude.server.DTO.Res.Detail.PowerReceiving.RoomPowerReceivingDetailDTO;
import com.jude.server.DTO.Res.Pagenation.PowerRecieving.GenPowerReceivingPageResDTO;
import com.jude.server.DTO.Res.Pagenation.PageResponse;
import com.jude.server.DTO.Res.Pagenation.PowerRecieving.PowerReceivingByFacilityPageResDTO;
import com.jude.server.DTO.Res.Pagenation.PowerRecieving.PowerReceivingPageResDTO;
import com.jude.server.DTO.Res.Pagenation.PowerRecieving.RoomPowerReceivingPageResDTO;
import com.jude.server.Entity.Equipment.Gen;
import com.jude.server.Entity.Facility;
import com.jude.server.Entity.PowerReceiving;
import com.jude.server.Entity.Room;
import com.jude.server.Repository.EmergencyEquipmentRepository;
import com.jude.server.Repository.FacilityRepository;
import com.jude.server.Repository.GenRepository;
import com.jude.server.Repository.PowerReceivingRepository;
import com.jude.server.Repository.RoomRepository;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PowerReceivingService {

    private final FacilityRepository facilityRepository;
    private final PowerReceivingRepository powerReceivingRepository;
    private final RoomRepository roomRepository;
    private final EmergencyEquipmentRepository emergencyEquipmentRepository;
    private final GenRepository genRepository;


    public PowerReceivingCreateResDTO powerReceivingCreateReq(
        PowerReceivingCreateReqDTO dto
    ) {
        Room roomEntity = roomRepository.findById(dto.getRoomId())
            .orElseThrow(() -> new IllegalArgumentException("Room ID가 없음"));

        PowerReceiving entity = PowerReceivingCreateReqDTO.toEntity(dto, roomEntity);
        PowerReceiving saved = powerReceivingRepository.save(entity);

        return PowerReceivingCreateResDTO.toRes(saved);
    }

    @Transactional(readOnly = true)
    public PageResponse<PowerReceivingByFacilityPageResDTO> powerReceivingByFacility(
        Pageable pageable
    ){
        /*
        1. Facility Page 조회
            1-1. facility Ids 모아줌 [1,2,3,4,5]
        2. Facility Id로 Rooms 조회
            2-1. room Ids 모아줌 [1,2,3,4,5]
        3. Room Id로 Power Receiving 조회
        4. Power Receiving 을 Room Ids 기준으로 DTO변환하여 Map생성 (1:1관계 -> toMap(id, dto 변환))
            4-1. prDTOMap : 1= (pr+room)DTO1, 2=(pr+room)DTO2, ...

        5. roomId로 EmergencyEquipment 조회 -> gen조회
            5-1. room ids 로 ee 타고 gen 조회 (repo에서 query로)
            5-2. gen DTO를 roomId 기준으로 그룹핑 (N:1 이므로 groupingBy)
        6. Room + PowerReceiving + Gens DTO 조립 (groupingBy + mapping)
        7. Room DTO를 facility ID기준으로 그룹핑
        8. Facility + Room DTO 조립 (map)
        9. PageResponse 변환
        */
        Page<Facility> facilityPage = facilityRepository.findAll(pageable);
        List<Long> facilityIds = facilityPage.getContent().stream().map(
            Facility::getFacilityId
        ).toList();

        List<Room> rooms = roomRepository.findByFacilityFacilityIdIn(facilityIds);
        List<Long> roomIds = rooms.stream().map(
            Room::getRoomId
        ).toList();

        List<PowerReceiving> prs = powerReceivingRepository.findByRoomRoomIdIn(roomIds);
        Map<Long, PowerReceivingPageResDTO> prDTOMap = prs.stream().collect(Collectors.toMap(
            pr -> pr.getRoom().getRoomId(),
            PowerReceivingPageResDTO::toRes
        ));

        List<Gen> gens = genRepository.findByRoomIds(roomIds);

        Map<Long, List<GenPowerReceivingPageResDTO>> genDTOMap = gens.stream()
            .collect(
                Collectors.groupingBy(
                    gen -> gen.getEmergencyEquipment()
                        .getRoom()
                        .getRoomId(),

                    Collectors.mapping(
                        GenPowerReceivingPageResDTO::toRes,
                        Collectors.toList()
                    )
                )
            );

        Map<Long, List<RoomPowerReceivingPageResDTO>> roomDTOMap = rooms.stream().collect(
            Collectors.groupingBy(
                room -> room.getFacility().getFacilityId(),
                Collectors.mapping(
                    room -> RoomPowerReceivingPageResDTO.toRes(
                        room,
                        prDTOMap.get(room.getRoomId()),
                        genDTOMap.getOrDefault(room.getRoomId(), List.of())
                    ), Collectors.toList()
                )));
        Page<PowerReceivingByFacilityPageResDTO> res = facilityPage.map(f -> {
            List<RoomPowerReceivingPageResDTO> roomsMap = roomDTOMap.getOrDefault(
                f.getFacilityId(),
                List.of()
            );

            return PowerReceivingByFacilityPageResDTO.toRes(f, roomsMap);
        });

        return PageResponse.toPageResponse(res);
    }

    @Transactional(readOnly = true)
    public PowerReceivingDetailResDTO detail(
        Long facilityId
    ) {
        /*
         * 1. Facility 조회
         */
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("Facility not found: " + facilityId));
        /*
         * 2. Facility에 속한 Room 조회
         */
        List<Room> rooms = roomRepository.findByFacilityId(facilityId);

        /*
         * Room이 없는 경우
         */
        if (rooms.isEmpty()) {
            return PowerReceivingDetailResDTO.toRes(facility, List.of());
        }

        /*
         * 3. Room ID 추출
         */

        List<Long> roomIds = rooms.stream()
                .map(Room::getRoomId)
                .toList();

        /*
         * 4. Room들의 수전설비 일괄 조회
         */

        List<PowerReceiving> powerReceivings = powerReceivingRepository.findByRoomIds(roomIds);

        /*
         * roomId -> PowerReceiving
         */

        Map<Long, PowerReceiving> powerReceivingMap =
            powerReceivings.stream()
                .collect(
                    Collectors.toMap(
                        powerReceiving ->
                            powerReceiving
                                .getRoom()
                                .getRoomId(),
                        Function.identity(),

                        /*
                         * 혹시 같은 Room에 데이터가
                         * 여러 개 존재하는 경우 첫 번째 사용
                         */
                        (first, second) -> first
                    )
                );

        /*
         * 5. Room들의 발전기 일괄 조회
         */
        List<Gen> gens = genRepository.findByRoomIds(roomIds);

        /*
         * roomId -> List<Gen>
         */
        Map<Long, List<Gen>> genMap = gens.stream()
                .collect(
                    Collectors.groupingBy(gen ->
                            gen.getEmergencyEquipment()
                                .getRoom()
                                .getRoomId()
                    )
                );

        /*
         * 6. Room별 DTO 조립
         */
        List<RoomPowerReceivingDetailDTO> roomDTOs = rooms.stream()
                .map(room -> {
                    Long roomId = room.getRoomId();
                    /*
                     * 현재 Room의 PowerReceiving
                     */
                    PowerReceiving powerReceiving = powerReceivingMap.get(roomId);

                    PowerReceivingDetailDTO powerReceivingDTO = PowerReceivingDetailDTO.toRes(powerReceiving);

                    /*
                     * 현재 Room의 발전기들
                     */
                    List<GenPowerReceivingDetailDTO> genDTOs =
                        genMap
                            .getOrDefault(roomId, List.of())
                            .stream()
                            .map(GenPowerReceivingDetailDTO::toRes)
                            .toList();

                    return RoomPowerReceivingDetailDTO.toRes(
                        room,
                        powerReceivingDTO,
                        genDTOs
                    );
                })
                .toList();

        /*
         * 7. 최종 Facility DTO 반환
         */
        return PowerReceivingDetailResDTO.toRes(facility, roomDTOs);
    }

    @Transactional
    public void update(
        Long facilityId,
        PowerReceivingUpdateReqDTO request
    ) {
        /*
         * 1. Facility 존재 확인
         */
        if (!facilityRepository.existsById(facilityId)) {
            throw new RuntimeException("Facility not found: " + facilityId);
        }

        /*
         * 2. 요청 데이터 확인
         */
        if (request.getRooms() == null) {
            return;
        }

        /*
         * 3. Room별 PowerReceiving 수정
         */

        for (
            PowerReceivingRoomUpdateDTO roomDTO : request.getRooms()
        ) {

            PowerReceiving powerReceiving = powerReceivingRepository.findForUpdate(
                        roomDTO.getPowerReceivingId(),
                        roomDTO.getRoomId(),
                        facilityId
                    ).orElseThrow(() -> new RuntimeException("PowerReceiving not found. "
                                + "facilityId="
                                + facilityId
                                + ", roomId="
                                + roomDTO.getRoomId()
                                + ", powerReceivingId="
                                + roomDTO.getPowerReceivingId()
                        )
                    );

            /*
             * DTO → Entity 변경
             */

            roomDTO.updateEntity(powerReceiving);
        }
    }
}
