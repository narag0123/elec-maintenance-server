package com.jude.server.DTO.Req.Create.EmergencyEquipmentSubDTOs;

import com.jude.server.Entity.Equipment.EmergencyEquipment;
import com.jude.server.Entity.Equipment.UPS;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UPSCreateReqDTO {
    private Long upsCapaKva;
    private Long upsVoltageV;

    public static UPS toEntity(
        UPSCreateReqDTO dto,
        EmergencyEquipment saved
    ){
        return UPS.builder()
            .upsCapaKva(dto.getUpsCapaKva())
            .upsVoltageV(dto.getUpsVoltageV())
            .emergencyEquipment(saved)
            .build();
    }
}
