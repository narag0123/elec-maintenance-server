package com.jude.server.DTO.Res.Detail.PowerReceiving;

import com.jude.server.Entity.PowerReceiving;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PowerReceivingDetailDTO {

    private Long powerReceivingId;
    private Long capaKw;
    private Long contractKw;
    private String inputCb;
    private String inputFrom;
    private Long voltageV;

    public static PowerReceivingDetailDTO toRes(
        PowerReceiving powerReceiving
    ) {

        if (powerReceiving == null) {
            return null;
        }

        return PowerReceivingDetailDTO.builder()
            .powerReceivingId(
                powerReceiving.getPowerReceivingId()
            )
            .capaKw(powerReceiving.getCapaKw())
            .contractKw(powerReceiving.getContractKw())
            .inputCb(powerReceiving.getInputCB())
            .inputFrom(powerReceiving.getInputFrom())
            .voltageV(powerReceiving.getVoltageV())
            .build();
    }
}
