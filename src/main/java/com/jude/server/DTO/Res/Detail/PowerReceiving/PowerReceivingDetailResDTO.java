package com.jude.server.DTO.Res.Detail.PowerReceiving;

import com.jude.server.DTO.Enum.FacilityType;
import com.jude.server.Entity.Facility;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PowerReceivingDetailResDTO {

    private Long facilityId;
    private String facilityName;
    private FacilityType facilityType;
    private String mngNo;

    private List<RoomPowerReceivingDetailDTO> rooms;

    public static PowerReceivingDetailResDTO toRes(
        Facility facility,
        List<RoomPowerReceivingDetailDTO> rooms
    ) {
        return PowerReceivingDetailResDTO.builder()
            .facilityId(facility.getFacilityId())
            .facilityName(facility.getFacilityName())
            .facilityType(facility.getFacilityType())
            .mngNo(facility.getMngNo())
            .rooms(rooms)
            .build();
    }
}