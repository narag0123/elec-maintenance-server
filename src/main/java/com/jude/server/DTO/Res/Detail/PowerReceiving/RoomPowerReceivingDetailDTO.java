package com.jude.server.DTO.Res.Detail.PowerReceiving;

import com.jude.server.DTO.Enum.RoomType;
import com.jude.server.Entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomPowerReceivingDetailDTO {

    private Long roomId;
    private String address;
    private RoomType roomType;

    private PowerReceivingDetailDTO powerReceiving;

    private List<GenPowerReceivingDetailDTO> gens;

    public static RoomPowerReceivingDetailDTO toRes(
        Room room,
        PowerReceivingDetailDTO powerReceiving,
        List<GenPowerReceivingDetailDTO> gens
    ) {
        return RoomPowerReceivingDetailDTO.builder()
            .roomId(room.getRoomId())
            .address(room.getAddress())
            .roomType(room.getRoomType())
            .powerReceiving(powerReceiving)
            .gens(gens)
            .build();
    }
}