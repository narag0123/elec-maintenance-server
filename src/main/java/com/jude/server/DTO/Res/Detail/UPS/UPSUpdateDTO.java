package com.jude.server.DTO.Res.Detail.UPS;


import com.jude.server.Entity.Equipment.EmergencyEquipment;
import com.jude.server.Entity.Equipment.UPS;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UPSUpdateDTO {

    private Long emergencyEquipmentId;
    private Long upsId;

    private String manufacturer;
    private LocalDate installDate;
    private String model;

    private Long upsVoltageV;
    private Long upsCapaKva;

    private List<UPSBatteryUpdateDTO> batteries;

    public void updateEmergencyEquipment(
        EmergencyEquipment emergencyEquipment
    ) {
        emergencyEquipment.setManufacturer(
            manufacturer
        );

        emergencyEquipment.setInstallDate(
            installDate
        );

        emergencyEquipment.setModel(
            model
        );
    }

    public void updateUPS(
        UPS ups
    ) {
        ups.setUpsVoltageV(
            upsVoltageV
        );

        ups.setUpsCapaKva(
            upsCapaKva
        );
    }
}