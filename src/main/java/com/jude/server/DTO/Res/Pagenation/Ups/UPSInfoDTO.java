package com.jude.server.DTO.Res.Pagenation.Ups;

import com.jude.server.Entity.Equipment.EmergencyEquipment;
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
public class UPSInfoDTO {

    private Long emergencyEquipmentId;
    private Long upsId;

    private String manufacturer;
    private LocalDate installDate;
    private String model;

    private Long upsVoltageV;
    private Long upsCapaKva;

    private List<UPSBatteryDTO> batteries;

    public static UPSInfoDTO toRes(
        UPS ups,
        List<UPSBatteryDTO> batteries
    ) {
        EmergencyEquipment ee = ups.getEmergencyEquipment();

        return UPSInfoDTO.builder()
            .emergencyEquipmentId(ee.getEmergencyEquipmentId())
            .upsId(ups.getUpsId())

            .manufacturer(ee.getManufacturer())
            .installDate(ee.getInstallDate())
            .model(ee.getModel())

            .upsVoltageV(ups.getUpsVoltageV())
            .upsCapaKva(ups.getUpsCapaKva())
            .batteries(batteries)
            .build();
    }
}