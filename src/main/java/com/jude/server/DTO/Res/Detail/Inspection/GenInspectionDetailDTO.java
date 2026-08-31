package com.jude.server.DTO.Res.Detail.Inspection;

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
public class GenInspectionDetailDTO {

    private Long emergencyEquipmentId;
    private Long genId;
    private GenType genType;
    private Long genCapaKva;

    public static GenInspectionDetailDTO toRes(
        Gen gen
    ) {
        return GenInspectionDetailDTO.builder()
            .emergencyEquipmentId(
                gen.getEmergencyEquipment()
                    .getEmergencyEquipmentId()
            )
            .genId(gen.getGenId())
            .genType(gen.getGenType())
            .genCapaKva(gen.getGenCapaKva())
            .build();
    }
}
