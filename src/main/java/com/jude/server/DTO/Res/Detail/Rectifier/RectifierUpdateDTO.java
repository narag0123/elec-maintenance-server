package com.jude.server.DTO.Res.Detail.Rectifier;

import com.jude.server.Entity.Equipment.EmergencyEquipment;
import com.jude.server.Entity.Equipment.Rectifier;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RectifierUpdateDTO {

    private Long emergencyEquipmentId;
    private Long recId;

    private String manufacturer;
    private LocalDate installDate;
    private String model;

    private Long recVoltageV;
    private Long recCapaA;

    private List<RectifierBatteryUpdateDTO> batteries;

    public void updateEmergencyEquipment(
        EmergencyEquipment emergencyEquipment
    ) {
        emergencyEquipment.setManufacturer(manufacturer);
        emergencyEquipment.setInstallDate(installDate);
        emergencyEquipment.setModel(model);
    }

    public void updateRectifier(
        Rectifier rectifier
    ) {
        rectifier.setRecVoltageV(recVoltageV);
        rectifier.setRecCapaA(recCapaA);
    }
}