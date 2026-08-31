package com.jude.server.Service;

import com.jude.server.DTO.Res.Detail.Rectifier.RectifierBatteryDetailDTO;
import com.jude.server.DTO.Res.Detail.Rectifier.RectifierBatteryUpdateDTO;
import com.jude.server.DTO.Res.Detail.Rectifier.RectifierDetailDTO;
import com.jude.server.DTO.Res.Detail.Rectifier.RectifierDetailResDTO;
import com.jude.server.DTO.Res.Detail.Rectifier.RectifierUpdateDTO;
import com.jude.server.DTO.Res.Detail.Rectifier.RectifierUpdateReqDTO;
import com.jude.server.DTO.Res.Rectifier.RectifierBatteryDTO;
import com.jude.server.DTO.Res.Rectifier.RectifierInfoDTO;
import com.jude.server.DTO.Res.Rectifier.RectifierResDTO;
import com.jude.server.Entity.Equipment.BAT;
import com.jude.server.Entity.Equipment.Rectifier;
import com.jude.server.Entity.Facility;
import com.jude.server.Repository.BATRepository;
import com.jude.server.Repository.FacilityRepository;
import com.jude.server.Repository.RectifierRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RectifierService {

    private final RectifierRepository rectifierRepository;
    private final BATRepository batRepository;
    private final FacilityRepository facilityRepository;

    @Transactional(readOnly = true)
    public List<RectifierResDTO> getAll() {

        /*
         * 1. REC 전체 조회
         *
         * Rectifier
         * → EmergencyEquipment
         * → Room
         * → Facility
         */
        List<Rectifier> rectifiers =
            rectifierRepository.findAllWithFacility();

        /*
         * 2. REC들의 EmergencyEquipment ID 추출
         */
        List<Long> emergencyEquipmentIds =
            rectifiers.stream()
                .map(rectifier ->
                    rectifier.getEmergencyEquipment()
                        .getEmergencyEquipmentId()
                )
                .distinct()
                .toList();

        /*
         * 3. BAT 일괄 조회
         */
        List<BAT> batteries =
            emergencyEquipmentIds.isEmpty()
                ? List.of()
                : batRepository.findByEmergencyEquipmentIds(
                    emergencyEquipmentIds
                );

        /*
         * 4. EmergencyEquipment ID → BAT 목록
         */
        Map<Long, List<BAT>> batteryMap =
            batteries.stream()
                .collect(Collectors.groupingBy(
                    bat ->
                        bat.getEmergencyEquipment()
                            .getEmergencyEquipmentId()
                ));

        /*
         * 5. Facility ID → Rectifier 목록
         */
        Map<Long, List<Rectifier>> facilityRectifierMap =
            rectifiers.stream()
                .collect(Collectors.groupingBy(
                    rectifier ->
                        rectifier.getEmergencyEquipment()
                            .getRoom()
                            .getFacility()
                            .getFacilityId()
                ));

        /*
         * 6. Facility ID → Facility
         */
        Map<Long, Facility> facilityMap =
            rectifiers.stream()
                .collect(Collectors.toMap(
                    rectifier ->
                        rectifier.getEmergencyEquipment()
                            .getRoom()
                            .getFacility()
                            .getFacilityId(),

                    rectifier ->
                        rectifier.getEmergencyEquipment()
                            .getRoom()
                            .getFacility(),

                    (existing, replacement) ->
                        existing
                ));

        /*
         * 7. Facility별 최종 DTO 조립
         */
        return facilityRectifierMap.entrySet()
            .stream()

            /*
             * facilityId 오름차순
             */
            .sorted(
                Map.Entry.comparingByKey()
            )

            .map(entry -> {

                Long facilityId =
                    entry.getKey();

                Facility facility =
                    facilityMap.get(
                        facilityId
                    );

                /*
                 * 해당 Facility의 REC들
                 */
                List<RectifierInfoDTO> rectifierDTOs =
                    entry.getValue()
                        .stream()
                        .map(rectifier -> {

                            Long emergencyEquipmentId =
                                rectifier
                                    .getEmergencyEquipment()
                                    .getEmergencyEquipmentId();

                            /*
                             * 현재 REC에 연결된 BAT
                             */
                            List<RectifierBatteryDTO> batteryDTOs =
                                batteryMap.getOrDefault(
                                        emergencyEquipmentId,
                                        List.of()
                                    )
                                    .stream()
                                    .map(
                                        RectifierBatteryDTO::toRes
                                    )
                                    .toList();

                            return RectifierInfoDTO.toRes(
                                rectifier,
                                batteryDTOs
                            );
                        })
                        .toList();

                return RectifierResDTO.toRes(
                    facility,
                    rectifierDTOs
                );
            })
            .toList();
    }

    @Transactional(readOnly = true)
    public RectifierDetailResDTO detail(
        Long facilityId
    ) {
        /*
         * 1. Facility 조회
         */
        Facility facility =
            facilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("Facility not found: " + facilityId));

        /*
         * 2. Facility의 Rectifier 조회
         */
        List<Rectifier> rectifiers = rectifierRepository.findAllByFacilityIdForDetail(facilityId);

        /*
         * Rectifier가 없으면 빈 배열
         */

        if (rectifiers.isEmpty()) {
            return RectifierDetailResDTO.toRes(
                facility,
                List.of()
            );

        }

        /*
         * 3. EmergencyEquipment ID 추출
         */

        List<Long> emergencyEquipmentIds = rectifiers.stream()
                .map(rectifier ->
                    rectifier
                        .getEmergencyEquipment()
                        .getEmergencyEquipmentId())
                .distinct()
                .toList();

        /*
         * 4. BAT 일괄 조회
         */

        List<BAT> batteries = batRepository.findByEmergencyEquipmentIds(emergencyEquipmentIds);

        /*
         * 5. EmergencyEquipment ID 기준 BAT 그룹핑
         */

        Map<Long, List<BAT>> batteryMap = batteries.stream()
                .collect(
                    Collectors.groupingBy(
                        bat ->
                            bat.getEmergencyEquipment()
                                .getEmergencyEquipmentId())
                );

        /*
         * 6. Rectifier DTO 조립
         */

        List<RectifierDetailDTO> rectifierDTOs = rectifiers.stream()
                .map(rectifier -> {
                    Long emergencyEquipmentId =
                        rectifier
                            .getEmergencyEquipment()
                            .getEmergencyEquipmentId();

                    List<RectifierBatteryDetailDTO> batteryDTOs = batteryMap
                            .getOrDefault(emergencyEquipmentId, List.of())
                            .stream()
                            .map(RectifierBatteryDetailDTO::toRes)
                            .toList();

                    return RectifierDetailDTO.toRes(
                        rectifier,
                        batteryDTOs
                    );

                })
                .toList();

        /*
         * 7. 최종 응답
         */
        return RectifierDetailResDTO.toRes(
            facility,
            rectifierDTOs
        );

    }

    @Transactional
    public void update(
        Long facilityId,
        RectifierUpdateReqDTO request
    ) {

        if (!facilityRepository.existsById(facilityId)) {
            throw new RuntimeException(
                "Facility not found: " + facilityId
            );
        }

        if (
            request == null
                || request.getRectifiers() == null
        ) {
            return;
        }

        for (
            RectifierUpdateDTO rectifierDTO
            : request.getRectifiers()
        ) {

            Rectifier rectifier =
                rectifierRepository.findForUpdate(
                        rectifierDTO.getRecId(),
                        rectifierDTO.getEmergencyEquipmentId(),
                        facilityId
                    )
                    .orElseThrow(
                        () ->
                            new RuntimeException(
                                "Rectifier not found. "
                                    + "facilityId="
                                    + facilityId
                                    + ", emergencyEquipmentId="
                                    + rectifierDTO
                                    .getEmergencyEquipmentId()
                                    + ", recId="
                                    + rectifierDTO.getRecId()
                            )
                    );

            /*
             * EmergencyEquipment 수정
             */
            rectifierDTO.updateEmergencyEquipment(
                rectifier.getEmergencyEquipment()
            );

            /*
             * Rectifier 수정
             */
            rectifierDTO.updateRectifier(
                rectifier
            );

            /*
             * 배터리 없으면 다음 Rectifier
             */
            if (
                rectifierDTO.getBatteries() == null
            ) {
                continue;
            }

            /*
             * BAT 수정
             */
            for (
                RectifierBatteryUpdateDTO batteryDTO
                : rectifierDTO.getBatteries()
            ) {

                BAT battery =
                    batRepository.findForRectifierUpdate(
                            batteryDTO.getBatId(),
                            rectifierDTO
                                .getEmergencyEquipmentId()
                        )
                        .orElseThrow(
                            () ->
                                new RuntimeException(
                                    "Battery not found. "
                                        + "batId="
                                        + batteryDTO.getBatId()
                                        + ", emergencyEquipmentId="
                                        + rectifierDTO
                                        .getEmergencyEquipmentId()
                                )
                        );

                batteryDTO.updateEntity(
                    battery
                );
            }
        }
    }
}