package com.jude.server.DTO.Res.Detail.Lighting;

import com.jude.server.Entity.Lighting;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LightingUpdateReqDTO {

    private Long tunnelIntegratedLedCount;
    private Long tunnelLedLampCount;
    private Long tunnelNormalCount;

    private Long streetLedCount;
    private Long streetNormalCount;

    private String lightingMemo;

    public void updateEntity(
        Lighting lighting
    ) {
        lighting.setTunnelIntegratedLedCount(
            tunnelIntegratedLedCount
        );

        lighting.setTunnelLedLampCount(
            tunnelLedLampCount
        );

        lighting.setTunnelNormalCount(
            tunnelNormalCount
        );

        lighting.setStreetLedCount(
            streetLedCount
        );

        lighting.setStreetNormalCount(
            streetNormalCount
        );

        lighting.setLightingMemo(
            lightingMemo
        );
    }
}