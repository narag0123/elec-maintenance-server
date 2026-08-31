package com.jude.server.DTO.Res.Detail.PowerReceiving;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PowerReceivingUpdateReqDTO {

    private List<PowerReceivingRoomUpdateDTO> rooms;
}