package com.jude.server.DTO.Req.Create;

import com.jude.server.Entity.PowerReceiving;
import com.jude.server.Entity.Room;
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
public class PowerReceivingCreateReqDTO {
    private Long capaKw;
    private Long voltageV;
    private Long contractKw;
    private String inputFrom;
    private String inputCB;

    private Long roomId;


    public static PowerReceiving toEntity(
        PowerReceivingCreateReqDTO dto,
        Room roomEntity
    ) {
        return PowerReceiving.builder()
            .capaKw(dto.getCapaKw())
            .contractKw(dto.getContractKw())
            .inputCB(dto.getInputCB())
            .inputFrom(dto.getInputFrom())
            .voltageV(dto.getVoltageV())
            .room(roomEntity)
            .build();
    }
}
