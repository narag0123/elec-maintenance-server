package com.jude.server.Service;

import com.jude.server.DTO.Res.Detail.Generator.GeneratorBatteryDetailDTO;
import com.jude.server.DTO.Res.Detail.Generator.GeneratorBatteryUpdateDTO;
import com.jude.server.DTO.Res.Detail.Generator.GeneratorDetailDTO;
import com.jude.server.DTO.Res.Detail.Generator.GeneratorDetailResDTO;
import com.jude.server.DTO.Res.Detail.Generator.GeneratorUpdateDTO;
import com.jude.server.DTO.Res.Detail.Generator.GeneratorUpdateReqDTO;
import com.jude.server.DTO.Res.Pagenation.Generator.GenBatteryDTO;
import com.jude.server.DTO.Res.Pagenation.Generator.GenInfoDTO;
import com.jude.server.DTO.Res.Pagenation.Generator.GenPageResDTO;
import com.jude.server.DTO.Res.Pagenation.PageResponse;
import com.jude.server.Entity.Equipment.BAT;
import com.jude.server.Entity.Equipment.Gen;
import com.jude.server.Entity.Facility;
import com.jude.server.Repository.BATRepository;
import com.jude.server.Repository.FacilityRepository;
import com.jude.server.Repository.GenRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GenService {

    private final FacilityRepository facilityRepository;
    private final GenRepository genRepository;
    private final BATRepository batRepository;

    @Transactional(readOnly = true)
    public PageResponse<GenPageResDTO> page(
        Pageable pageable
    ) {
        /*
         * 1. 발전기가 존재하는 Facility를 페이지 단위로 조회
         * 2. 현재 페이지의 Facility ID 추출
         * 3. Facility ID로 발전기 전체 일괄 조회
         * 4. 발전기의 EmergencyEquipment ID 추출
         * 5. EmergencyEquipment ID로 BAT 전체 일괄 조회
         * 6. Facility ID 기준 발전기 Map 생성
         * 7. EmergencyEquipment ID 기준 BAT Map 생성
         * 8. Facility별 발전기 및 BAT DTO 조립
         * 9. PageResponse 변환
         */

        Page<Facility> facilityPage =
            facilityRepository.findAllHasGen(pageable);

        List<Facility> facilities =
            facilityPage.getContent();

        List<Long> facilityIds =
            facilities.stream()
                .map(Facility::getFacilityId)
                .toList();

        List<Gen> gens =
            facilityIds.isEmpty()
                ? List.of()
                : genRepository.findByFacilityIds(
                    facilityIds
                );

        List<Long> emergencyEquipmentIds =
            gens.stream()
                .map(gen ->
                    gen.getEmergencyEquipment()
                        .getEmergencyEquipmentId()
                )
                .distinct()
                .toList();

        List<BAT> batteries =
            emergencyEquipmentIds.isEmpty()
                ? List.of()
                : batRepository.findByEmergencyEquipmentIds(
                    emergencyEquipmentIds
                );

        Map<Long, List<Gen>> facilityGenMap =
            gens.stream()
                .collect(Collectors.groupingBy(
                    gen ->
                        gen.getEmergencyEquipment()
                            .getRoom()
                            .getFacility()
                            .getFacilityId()
                ));

        Map<Long, List<BAT>> batteryMap =
            batteries.stream()
                .collect(Collectors.groupingBy(
                    bat ->
                        bat.getEmergencyEquipment()
                            .getEmergencyEquipmentId()
                ));

        Page<GenPageResDTO> result =
            facilityPage.map(facility -> {

                List<GenInfoDTO> genDTOs =
                    facilityGenMap.getOrDefault(
                            facility.getFacilityId(),
                            List.of()
                        )
                        .stream()
                        .map(gen -> {

                            Long emergencyEquipmentId =
                                gen.getEmergencyEquipment()
                                    .getEmergencyEquipmentId();

                            List<GenBatteryDTO> batteryDTOs =
                                batteryMap.getOrDefault(
                                        emergencyEquipmentId,
                                        List.of()
                                    )
                                    .stream()
                                    .map(GenBatteryDTO::toRes)
                                    .toList();

                            return GenInfoDTO.toRes(
                                gen,
                                batteryDTOs
                            );
                        })
                        .toList();

                return GenPageResDTO.toRes(
                    facility,
                    genDTOs
                );
            });

        return PageResponse.toPageResponse(result);
    }

    @Transactional(readOnly = true)
    public GeneratorDetailResDTO detail(
        Long facilityId
    ) {
        /*
         * 1. Facility 조회
         */
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("Facility not found: " + facilityId));

        /*
         * 2. Facility에 속한 발전기 조회
         */
        List<Gen> gens = genRepository.findAllByFacilityIdForDetail(facilityId);

        /*
         * 발전기가 없는 경우
         */
        if (gens.isEmpty()) {
            return GeneratorDetailResDTO.toRes(facility, List.of());
        }

        /*
         * 3. 발전기의 EmergencyEquipment ID 추출
         */
        List<Long> emergencyEquipmentIds =
            gens.stream()
                .map(gen -> gen.getEmergencyEquipment().getEmergencyEquipmentId())
                .distinct()
                .toList();
        /*
         * 4. 해당 발전기들의 BAT 일괄 조회
         */
        List<BAT> batteries = batRepository.findByEmergencyEquipmentIds(emergencyEquipmentIds);

        /*
         * 5.
         *
         * emergencyEquipmentId
         *      ↓
         * List<BAT>
         */

        Map<Long, List<BAT>> batteryMap = batteries.stream()
                .collect(
                    Collectors.groupingBy(
                        bat ->
                            bat.getEmergencyEquipment()
                                .getEmergencyEquipmentId())
                );

        /*
         * 6. Gen DTO 조립
         */
        List<GeneratorDetailDTO> genDTOs = gens.stream()
                .map(gen -> {
                    Long emergencyEquipmentId = gen.getEmergencyEquipment().getEmergencyEquipmentId();

                    /*
                     * 현재 발전기의 BAT
                     */
                    List<GeneratorBatteryDetailDTO> batteryDTOs =
                        batteryMap
                            .getOrDefault(
                                emergencyEquipmentId,
                                List.of()
                            )
                            .stream()
                            .map(GeneratorBatteryDetailDTO::toRes)
                            .toList();

                    return GeneratorDetailDTO.toRes(
                        gen,
                        batteryDTOs
                    );
                })
            .toList();

        /*
         * 7. 최종 응답
         */
        return GeneratorDetailResDTO.toRes(facility, genDTOs);
    }

    @Transactional
    public void update(
        Long facilityId,
        GeneratorUpdateReqDTO request
    ) {

        /*
         * 1. Facility 존재 확인
         */
        if (!facilityRepository.existsById(facilityId)) {
            throw new RuntimeException(
                "Facility not found: " + facilityId
            );
        }

        /*
         * gens가 없으면 수정할 내용 없음
         */
        if (request.getGens() == null) {
            return;
        }

        /*
         * 2. Generator 하나씩 수정
         */
        for (GeneratorUpdateDTO genDTO : request.getGens()) {

            /*
             * facilityId
             * emergencyEquipmentId
             * genId
             *
             * 세 관계가 전부 맞는 발전기만 조회
             */
            Gen gen =
                genRepository.findForUpdate(
                        genDTO.getGenId(),
                        genDTO.getEmergencyEquipmentId(),
                        facilityId
                    )
                    .orElseThrow(() ->
                        new RuntimeException(
                            "Generator not found. "
                                + "facilityId=" + facilityId
                                + ", emergencyEquipmentId="
                                + genDTO.getEmergencyEquipmentId()
                                + ", genId="
                                + genDTO.getGenId()
                        )
                    );

            /*
             * 3. EmergencyEquipment 수정
             */
            genDTO.updateEmergencyEquipment(
                gen.getEmergencyEquipment()
            );

            /*
             * 4. Gen 수정
             */
            genDTO.updateGen(
                gen
            );

            /*
             * 5. Battery가 없다면 다음 Generator
             */
            if (genDTO.getBatteries() == null) {
                continue;
            }

            /*
             * 6. Battery 수정
             */
            for (
                GeneratorBatteryUpdateDTO batteryDTO
                : genDTO.getBatteries()
            ) {

                BAT battery =
                    batRepository.findForGeneratorUpdate(
                            batteryDTO.getBatId(),
                            genDTO.getEmergencyEquipmentId()
                        )
                        .orElseThrow(() ->
                            new RuntimeException(
                                "Battery not found. "
                                    + "batId="
                                    + batteryDTO.getBatId()
                                    + ", emergencyEquipmentId="
                                    + genDTO.getEmergencyEquipmentId()
                            )
                        );

                batteryDTO.updateEntity(
                    battery
                );
            }
        }
    }
}