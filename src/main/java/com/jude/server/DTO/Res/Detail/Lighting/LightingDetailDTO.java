package com.jude.server.DTO.Res.Detail.Lighting;

import com.jude.server.Entity.Lighting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LightingDetailDTO {

    private Long lightingId;

    private Long tunnelIntegratedLedCount;
    private Long tunnelLedLampCount;
    private Long tunnelNormalCount;

    private Long streetLedCount;
    private Long streetNormalCount;

    private String lightingMemo;

    public static LightingDetailDTO toRes(
        Lighting lighting
    ) {
        if (lighting == null) {
            return null;
        }

        return LightingDetailDTO.builder()
            .lightingId(lighting.getLightingId())
            .tunnelIntegratedLedCount(
                lighting.getTunnelIntegratedLedCount()
            )
            .tunnelLedLampCount(
                lighting.getTunnelLedLampCount()
            )
            .tunnelNormalCount(
                lighting.getTunnelNormalCount()
            )
            .streetLedCount(
                lighting.getStreetLedCount()
            )
            .streetNormalCount(
                lighting.getStreetNormalCount()
            )
            .lightingMemo(
                lighting.getLightingMemo()
            )
            .build();
    }
}