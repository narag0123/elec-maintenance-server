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
public class TunnelLightingDTO {

    private Long integratedLedCount;
    private Long ledLampCount;
    private Long normalCount;

    public static TunnelLightingDTO toRes(
        Lighting lighting
    ) {
        return TunnelLightingDTO.builder()
            .integratedLedCount(
                lighting.getTunnelIntegratedLedCount()
            )
            .ledLampCount(
                lighting.getTunnelLedLampCount()
            )
            .normalCount(
                lighting.getTunnelNormalCount()
            )
            .build();
    }
}