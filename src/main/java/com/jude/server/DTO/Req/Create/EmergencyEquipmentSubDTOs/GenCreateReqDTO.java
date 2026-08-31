package com.jude.server.DTO.Req.Create.EmergencyEquipmentSubDTOs;

import com.jude.server.DTO.Enum.GenType;
import com.jude.server.Entity.Equipment.EmergencyEquipment;
import com.jude.server.Entity.Equipment.Gen;
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
public class GenCreateReqDTO {
    private Long genCapaKva;
    private GenType genType;
    private Long genVoltageV;

    public static Gen toEntity(
        GenCreateReqDTO dto,
        EmergencyEquipment emergencyEquipment
    ) {
        return Gen.builder()
            .genCapaKva(dto.getGenCapaKva())
            .genType(dto.getGenType())
            .genVoltageV(dto.getGenVoltageV())
            .emergencyEquipment(emergencyEquipment)
            .build();
    }
}
