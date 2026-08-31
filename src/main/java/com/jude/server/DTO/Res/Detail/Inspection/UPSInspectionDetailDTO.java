package com.jude.server.DTO.Res.Detail.Inspection;

import com.jude.server.Entity.Equipment.UPS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UPSInspectionDetailDTO {

    private Long emergencyEquipmentId;
    private Long upsId;
    private Long upsCapaKva;

    public static UPSInspectionDetailDTO toRes(
        UPS ups
    ) {
        return UPSInspectionDetailDTO.builder()
            .emergencyEquipmentId(
                ups.getEmergencyEquipment()
                    .getEmergencyEquipmentId()
            )
            .upsId(ups.getUpsId())
            .upsCapaKva(ups.getUpsCapaKva())
            .build();
    }
}

