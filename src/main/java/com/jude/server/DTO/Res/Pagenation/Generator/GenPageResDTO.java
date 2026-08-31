package com.jude.server.DTO.Res.Pagenation.Generator;

import com.jude.server.Entity.Facility;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenPageResDTO {

    private Long facilityId;
    private String mngNo;
    private String facilityName;

    private List<GenInfoDTO> gens;

    public static GenPageResDTO toRes(
        Facility facility,
        List<GenInfoDTO> gens
    ) {
        return GenPageResDTO.builder()
            .facilityId(facility.getFacilityId())
            .mngNo(facility.getMngNo())
            .facilityName(facility.getFacilityName())
            .gens(gens)
            .build();
    }
}