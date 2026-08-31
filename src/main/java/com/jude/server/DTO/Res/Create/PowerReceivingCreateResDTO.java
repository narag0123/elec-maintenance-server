package com.jude.server.DTO.Res.Create;

import com.jude.server.Entity.PowerReceiving;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class PowerReceivingCreateResDTO {

    private Long powerRecievingId;

    public static PowerReceivingCreateResDTO toRes(
        PowerReceiving saved
    ) {
        return PowerReceivingCreateResDTO.builder()
            .powerRecievingId(saved.getPowerReceivingId())
            .build();
    }
}
