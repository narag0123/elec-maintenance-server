package com.jude.server.DTO.Res.Rectifier;

import com.jude.server.Entity.Equipment.EmergencyEquipment;
import com.jude.server.Entity.Equipment.Rectifier;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RectifierInfoDTO {

    private Long emergencyEquipmentId;
    private Long recId;

    private String manufacturer;
    private LocalDate installDate;
    private String model;

    private Long recVoltageV;
    private Long recCapaA;

    private List<RectifierBatteryDTO> batteries;

    public static RectifierInfoDTO toRes(
        Rectifier rectifier,
        List<RectifierBatteryDTO> batteries
    ) {

        EmergencyEquipment ee =
            rectifier.getEmergencyEquipment();

        return RectifierInfoDTO.builder()
            .emergencyEquipmentId(
                ee.getEmergencyEquipmentId()
            )
            .recId(
                rectifier.getRecId()
            )
            .manufacturer(
                ee.getManufacturer()
            )
            .installDate(
                ee.getInstallDate()
            )
//            .model(
//                ee.getModel()
//            )
            .recVoltageV(
                rectifier.getRecVoltageV()
            )
            .recCapaA(
                rectifier.getRecCapaA()
            )
            .batteries(
                batteries
            )
            .build();
    }
}