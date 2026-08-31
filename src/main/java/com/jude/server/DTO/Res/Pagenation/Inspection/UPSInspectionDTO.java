package com.jude.server.DTO.Res.Pagenation.Inspection;

import com.jude.server.Entity.Equipment.UPS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UPSInspectionDTO {

    private Long emergencyEquipmentId;
    private Long upsId;
    private Long upsCapaKva;

    public static UPSInspectionDTO toRes(
        UPS ups
    ) {
        return UPSInspectionDTO.builder()
            .emergencyEquipmentId(ups.getEmergencyEquipment().getEmergencyEquipmentId())
            .upsId(ups.getUpsId())
            .upsCapaKva(ups.getUpsCapaKva())
            .build();
    }
}
