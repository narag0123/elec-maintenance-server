package com.jude.server.DTO.Res.Pagenation.PowerRecieving;

import com.jude.server.DTO.Enum.FacilityType;
import com.jude.server.Entity.Facility;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PowerReceivingByFacilityPageResDTO {

    private Long facilityId;
    private String facilityName;
    private FacilityType facilityType;
    private String mngNo;

    private List<RoomPowerReceivingPageResDTO> rooms;


    public static PowerReceivingByFacilityPageResDTO toRes(
        Facility facility,
        List<RoomPowerReceivingPageResDTO> rooms
    ) {
        return PowerReceivingByFacilityPageResDTO.builder()
            .facilityId(facility.getFacilityId())
            .facilityName(facility.getFacilityName())
            .facilityType(facility.getFacilityType())
            .mngNo(facility.getMngNo())
            .rooms(rooms)
            .build();
    }
}