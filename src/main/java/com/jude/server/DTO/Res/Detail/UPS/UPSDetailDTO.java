package com.jude.server.DTO.Res.Detail.UPS;

import com.jude.server.Entity.Equipment.UPS;
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
public class UPSDetailDTO {

    private Long emergencyEquipmentId;
    private Long upsId;

    private String manufacturer;
    private LocalDate installDate;
    private String model;

    private Long upsVoltageV;
    private Long upsCapaKva;

    private List<UPSBatteryDetailDTO> batteries;

    public static UPSDetailDTO toRes(
        UPS ups,
        List<UPSBatteryDetailDTO> batteries
    ) {
        return UPSDetailDTO.builder()
            .emergencyEquipmentId(
                ups.getEmergencyEquipment()
                    .getEmergencyEquipmentId()
            )
            .upsId(
                ups.getUpsId()
            )
            .manufacturer(
                ups.getEmergencyEquipment()
                    .getManufacturer()
            )
            .installDate(
                ups.getEmergencyEquipment()
                    .getInstallDate()
            )
            .model(
                ups.getEmergencyEquipment()
                    .getModel()
            )
            .upsVoltageV(
                ups.getUpsVoltageV()
            )
            .upsCapaKva(
                ups.getUpsCapaKva()
            )
            .batteries(
                batteries
            )
            .build();
    }
}