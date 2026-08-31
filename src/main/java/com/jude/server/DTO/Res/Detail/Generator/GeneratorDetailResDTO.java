package com.jude.server.DTO.Res.Detail.Generator;

import com.jude.server.Entity.Facility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorDetailResDTO {

    private Long facilityId;
    private String mngNo;
    private String facilityName;

    private List<GeneratorDetailDTO> gens;

    public static GeneratorDetailResDTO toRes(
        Facility facility,
        List<GeneratorDetailDTO> gens
    ) {
        return GeneratorDetailResDTO.builder()
            .facilityId(facility.getFacilityId())
            .mngNo(facility.getMngNo())
            .facilityName(facility.getFacilityName())
            .gens(gens)
            .build();
    }
}