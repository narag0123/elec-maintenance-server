package com.jude.server.DTO.Res.Detail.Rectifier;

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
public class RectifierDetailDTO {

    private Long emergencyEquipmentId;
    private Long recId;

    private String manufacturer;
    private LocalDate installDate;
    private String model;

    private Long recVoltageV;
    private Long recCapaA;

    private List<RectifierBatteryDetailDTO> batteries;

    public static RectifierDetailDTO toRes(
        Rectifier rectifier,
        List<RectifierBatteryDetailDTO> batteries
    ) {
        return RectifierDetailDTO.builder()
            .emergencyEquipmentId(
                rectifier.getEmergencyEquipment()
                    .getEmergencyEquipmentId()
            )
            .recId(
                rectifier.getRecId()
            )
            .manufacturer(
                rectifier.getEmergencyEquipment()
                    .getManufacturer()
            )
            .installDate(
                rectifier.getEmergencyEquipment()
                    .getInstallDate()
            )
            .model(
                rectifier.getEmergencyEquipment()
                    .getModel()
            )
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