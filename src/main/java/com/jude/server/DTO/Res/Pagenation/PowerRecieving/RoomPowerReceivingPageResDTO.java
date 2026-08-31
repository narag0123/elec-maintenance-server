package com.jude.server.DTO.Res.Pagenation.PowerRecieving;

import com.jude.server.DTO.Enum.RoomType;
import com.jude.server.Entity.Equipment.Gen;
import com.jude.server.Entity.Room;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomPowerReceivingPageResDTO {

    private Long roomId;
    private String address;
    private RoomType roomType;

    private PowerReceivingPageResDTO powerReceiving;
    private List<GenPowerReceivingPageResDTO> gens;

    public static RoomPowerReceivingPageResDTO toRes(
        Room room,
        PowerReceivingPageResDTO powerReceiving,
        List<GenPowerReceivingPageResDTO> gens
    ) {
        return RoomPowerReceivingPageResDTO.builder()
            .roomId(room.getRoomId())
            .address(room.getAddress())
            .roomType(room.getRoomType())
            .powerReceiving(powerReceiving)
            .gens(gens)
            .build();
    }
}