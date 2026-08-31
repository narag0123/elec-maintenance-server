package com.jude.server.DTO.Res.Pagenation.Inspection;

import com.jude.server.DTO.Enum.RoomType;
import com.jude.server.Entity.PowerReceiving;
import com.jude.server.Entity.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomInspectionDTO {

    private Long roomId;
    private RoomType roomType;

    private String address;

    private Long contractKw;
    private Long voltageV;

    public static RoomInspectionDTO toRes(
        Room room,
        PowerReceiving powerReceiving
    ) {
        if (room == null) {
            return null;
        }

        return RoomInspectionDTO.builder()
            .roomId(room.getRoomId())
            .roomType(room.getRoomType())
            .address(room.getAddress())
            .contractKw(
                powerReceiving == null
                    ? null
                    : powerReceiving.getContractKw()
            )
            .voltageV(
                powerReceiving == null
                    ? null
                    : powerReceiving.getVoltageV()
            )
            .build();
    }
}
