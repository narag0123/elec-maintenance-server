package com.jude.server.DTO.Res.Detail.UPS;

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
public class UPSDetailResDTO {

    private Long facilityId;
    private String mngNo;
    private String facilityName;

    private List<UPSDetailDTO> ups;

    public static UPSDetailResDTO toRes(
        Facility facility,
        List<UPSDetailDTO> ups
    ) {
        return UPSDetailResDTO.builder()
            .facilityId(facility.getFacilityId())
            .mngNo(facility.getMngNo())
            .facilityName(facility.getFacilityName())
            .ups(ups)
            .build();
    }
}