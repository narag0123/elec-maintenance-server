package com.jude.server.DTO.Res.Pagenation.Lighting;

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
public class LightingResDTO {

    private Long lightingId;

    private Long facilityId;
    private String mngNo;
    private String facilityName;

    private TunnelLightingDTO tunnel;
    private StreetLightingDTO street;

    private String lightingMemo;

    public static LightingResDTO toRes(
        Facility facility,
        Lighting lighting
    ) {
        return LightingResDTO.builder()
            .lightingId(
                lighting.getLightingId()
            )
            .facilityId(
                facility.getFacilityId()
            )
            .mngNo(
                facility.getMngNo()
            )
            .facilityName(
                facility.getFacilityName()
            )
            .tunnel(
                TunnelLightingDTO.toRes(lighting)
            )
            .street(
                StreetLightingDTO.toRes(lighting)
            )
            .lightingMemo(lighting.getLightingMemo())
            .build();
    }
}