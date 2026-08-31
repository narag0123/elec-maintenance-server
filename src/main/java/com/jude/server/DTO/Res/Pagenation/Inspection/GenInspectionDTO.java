package com.jude.server.DTO.Res.Pagenation.Inspection;

import com.jude.server.DTO.Enum.GenType;
import com.jude.server.Entity.Equipment.Gen;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenInspectionDTO {

    private Long emergencyEquipmentId;
    private Long genId;
    private GenType genType;
    private Long genCapaKva;

    public static GenInspectionDTO toRes(
        Gen gen
    ){
        return GenInspectionDTO.builder()
            .genId(gen.getGenId())
            .emergencyEquipmentId(gen.getEmergencyEquipment().getEmergencyEquipmentId())
            .genType(gen.getGenType())
            .genCapaKva(gen.getGenCapaKva())
            .build();
    }
}