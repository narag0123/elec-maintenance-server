package com.jude.server.DTO.Req.Create.EmergencyEquipmentSubDTOs;

import com.jude.server.Entity.Equipment.EmergencyEquipment;
import com.jude.server.Entity.Equipment.EmergencyLoad;
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
public class EmergencyLoadCreateReqDTO {
    private String emergencyLoadCubicleNo;
    private String emergencyLoadName;

    private Long emergencyEquipmentId;

    public static EmergencyLoad toEntity(
        EmergencyLoadCreateReqDTO dto,
        EmergencyEquipment emergencyEquipment
    ){
        return EmergencyLoad.builder()
            .emergencyLoadCubicleNo(dto.getEmergencyLoadCubicleNo())
            .emergencyLoadName(dto.getEmergencyLoadName())
            .emergencyEquipment(emergencyEquipment)
            .build();
    }
}
