package com.jude.server.DTO.Res.Detail.Lighting;

import com.jude.server.DTO.Enum.FacilityType;
import com.jude.server.Entity.Facility;
import com.jude.server.Entity.Lighting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LightingDetailResDTO {
    private Long facilityId;
    private String mngNo;
    private String facilityName;
    private FacilityType facilityType;
    private LightingDetailDTO lighting;

    public static LightingDetailResDTO toRes(
        Facility facility,
        Lighting lighting
    ) {

        return LightingDetailResDTO.builder()
            .facilityId(facility.getFacilityId())
            .mngNo(facility.getMngNo())
            .facilityName(facility.getFacilityName())
            .facilityType(facility.getFacilityType())

            .lighting(LightingDetailDTO.toRes(lighting))
            .build();

    }
}
