package com.jude.server.DTO.Res.Pagenation.Ups;

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
public class UPSPageResDTO {

    private Long facilityId;
    private String mngNo;
    private String facilityName;

    private List<UPSInfoDTO> ups;


    public static UPSPageResDTO toRes(
        Facility facility,
        List<UPSInfoDTO> ups
    ) {
        return UPSPageResDTO.builder()
            .facilityId(facility.getFacilityId())
            .mngNo(facility.getMngNo())
            .facilityName(facility.getFacilityName())
            .ups(ups)
            .build();
    }
}