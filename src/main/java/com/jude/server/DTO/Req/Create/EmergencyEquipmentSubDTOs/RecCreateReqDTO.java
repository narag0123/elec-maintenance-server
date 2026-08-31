package com.jude.server.DTO.Req.Create.EmergencyEquipmentSubDTOs;

import com.jude.server.Entity.Equipment.EmergencyEquipment;
import com.jude.server.Entity.Equipment.Rectifier;
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
public class RecCreateReqDTO {
    private Long recCapaA;
    private Long recVoltageV;

    public static Rectifier toEntity(
        RecCreateReqDTO dto,
        EmergencyEquipment emergencyEquipment
    ){
        return Rectifier.builder()
            .recCapaA(dto.getRecCapaA())
            .recVoltageV(dto.getRecVoltageV())
            .emergencyEquipment(emergencyEquipment)
            .build();
    }
}
