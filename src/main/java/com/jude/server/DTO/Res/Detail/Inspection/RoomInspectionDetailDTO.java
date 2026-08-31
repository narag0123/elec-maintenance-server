package com.jude.server.DTO.Res.Detail.Inspection;

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
public class RoomInspectionDetailDTO {

    private Long roomId;
    private RoomType roomType;
    private String address;

    private Long contractKw;
    private Long voltageV;

    public static RoomInspectionDetailDTO toRes(
        Room room,
        PowerReceiving powerReceiving
    ) {
        if (room == null) {
            return null;
        }

        return RoomInspectionDetailDTO.builder()
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