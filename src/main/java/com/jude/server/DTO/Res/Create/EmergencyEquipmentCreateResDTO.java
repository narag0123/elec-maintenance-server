package com.jude.server.DTO.Res.Create;

import com.jude.server.DTO.Enum.EmergencyEquipmentType;
import com.jude.server.Entity.Equipment.EmergencyEquipment;
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
public class EmergencyEquipmentCreateResDTO {
    private Long emergencyEquipmentId;
    private EmergencyEquipmentType type;

    public static EmergencyEquipmentCreateResDTO toRes(
        EmergencyEquipment e,
        EmergencyEquipmentType type
    ) {
        return EmergencyEquipmentCreateResDTO.builder()
            .emergencyEquipmentId(e.getEmergencyEquipmentId())
            .type(type)
            .build();
    }
}
