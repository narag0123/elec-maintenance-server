package com.jude.server.DTO.Res.Pagenation.Lighting;

import com.jude.server.Entity.Lighting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StreetLightingDTO {

    private Long ledCount;
    private Long normalCount;

    public static StreetLightingDTO toRes(
        Lighting lighting
    ) {
        return StreetLightingDTO.builder()
            .ledCount(
                lighting.getStreetLedCount()
            )
            .normalCount(
                lighting.getStreetNormalCount()
            )
            .build();
    }
}