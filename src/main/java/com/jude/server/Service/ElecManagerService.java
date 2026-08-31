package com.jude.server.Service;

import com.jude.server.DTO.Res.Manager.ElecManagerInfoDTO;
import com.jude.server.DTO.Res.Manager.ElecManagerResDTO;
import com.jude.server.Entity.Equipment.Gen;
import com.jude.server.Entity.Facility;
import com.jude.server.Entity.Inspection.ElecManager;
import com.jude.server.Entity.PowerReceiving;
import com.jude.server.Entity.Room;
import com.jude.server.Repository.ElecManagerRepository;
import com.jude.server.Repository.GenRepository;
import com.jude.server.Repository.PowerReceivingRepository;
import com.jude.server.Repository.RoomRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ElecManagerService {

    private final ElecManagerRepository elecManagerRepository;
    private final RoomRepository roomRepository;
    private final PowerReceivingRepository powerReceivingRepository;
    private final GenRepository genRepository;

    @Transactional(readOnly = true)
    public List<ElecManagerResDTO> getAll() {

        List<ElecManager> elecManagers =
            elecManagerRepository.findAllWithFacility();

        List<Long> facilityIds =
            elecManagers.stream()
                .map(manager ->
                    manager.getFacility()
                        .getFacilityId()
                )
                .distinct()
                .toList();

        if (facilityIds.isEmpty()) {
            return List.of();
        }

        List<Room> rooms =
            roomRepository.findByFacilityFacilityIdIn(
                facilityIds
            );

        List<Long> roomIds =
            rooms.stream()
                .map(Room::getRoomId)
                .toList();

        List<PowerReceiving> powerReceivings =
            roomIds.isEmpty()
                ? List.of()
                : powerReceivingRepository
                    .findByRoomRoomIdIn(roomIds);

        List<Gen> gens =
            roomIds.isEmpty()
                ? List.of()
                : genRepository.findByRoomIds(roomIds);

        Map<Long, List<ElecManager>> managerMap =
            elecManagers.stream()
                .collect(Collectors.groupingBy(
                    manager ->
                        manager.getFacility()
                            .getFacilityId()
                ));

        Map<Long, Facility> facilityMap =
            elecManagers.stream()
                .collect(Collectors.toMap(
                    manager ->
                        manager.getFacility()
                            .getFacilityId(),
                    ElecManager::getFacility,
                    (existing, replacement) -> existing
                ));

        Map<Long, Long> roomFacilityMap =
            rooms.stream()
                .collect(Collectors.toMap(
                    Room::getRoomId,
                    room ->
                        room.getFacility()
                            .getFacilityId()
                ));

        Map<Long, Long> powerReceivingMap =
            powerReceivings.stream()
                .filter(pr ->
                    pr.getContractKw() != null
                )
                .collect(Collectors.groupingBy(
                    pr ->
                        roomFacilityMap.get(
                            pr.getRoom().getRoomId()
                        ),
                    Collectors.summingLong(
                        PowerReceiving::getContractKw
                    )
                ));

        Map<Long, Long> generatorMap =
            gens.stream()
                .filter(gen ->
                    gen.getGenCapaKva() != null
                )
                .collect(Collectors.groupingBy(
                    gen ->
                        roomFacilityMap.get(
                            gen.getEmergencyEquipment()
                                .getRoom()
                                .getRoomId()
                        ),
                    Collectors.summingLong(
                        Gen::getGenCapaKva
                    )
                ));

        return facilityMap.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByKey())
            .map(entry -> {

                Long facilityId = entry.getKey();
                Facility facility = entry.getValue();

                List<ElecManagerInfoDTO> managerDTOs =
                    managerMap.getOrDefault(
                            facilityId,
                            List.of()
                        )
                        .stream()
                        .map(ElecManagerInfoDTO::toRes)
                        .toList();

                return ElecManagerResDTO.toRes(
                    facility,
                    powerReceivingMap.getOrDefault(
                        facilityId,
                        0L
                    ),
                    generatorMap.getOrDefault(
                        facilityId,
                        0L
                    ),
                    managerDTOs
                );
            })
            .toList();
    }
}
