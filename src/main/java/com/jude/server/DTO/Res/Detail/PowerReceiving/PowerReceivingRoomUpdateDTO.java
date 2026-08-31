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
public class PowerReceivingRoomUpdateDTO {

    private Long roomId;
    private Long powerReceivingId;

    private Long capaKw;
    private Long contractKw;

    private String inputCb;
    private String inputFrom;

    private Long voltageV;

    public void updateEntity(
        PowerReceiving powerReceiving
    ) {
        powerReceiving.setCapaKw(capaKw);
        powerReceiving.setContractKw(contractKw);
        powerReceiving.setInputCB(inputCb);
        powerReceiving.setInputFrom(inputFrom);
        powerReceiving.setVoltageV(voltageV);
    }
}