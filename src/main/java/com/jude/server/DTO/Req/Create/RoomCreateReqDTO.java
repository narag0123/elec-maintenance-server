package com.jude.server.DTO.Req.Create;

import com.jude.server.DTO.Enum.RoomType;
import com.jude.server.Entity.Facility;
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
public class RoomCreateReqDTO {
    private RoomType roomType;
    private String address;
    private Long facilityId;

    public static Room toEntity(
        RoomCreateReqDTO dto,
        Facility facility
    ){
        return Room.builder()
            .roomType(dto.getRoomType())
            .address(dto.getAddress())
            .facility(facility)
            .build();
    }
}
