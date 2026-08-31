package com.jude.server.DTO.Res.Create;

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
public class RoomCreateResDTO {
    private Long roomId;

    public static RoomCreateResDTO toRes(Room e) {
        return RoomCreateResDTO.builder()
            .roomId(e.getRoomId())
            .build();
    }
}
