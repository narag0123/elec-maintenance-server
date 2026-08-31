package com.jude.server.DTO.Res.Detail.Rectifier;

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
public class RectifierDetailResDTO {

    private Long facilityId;
    private String mngNo;
    private String facilityName;

    private List<RectifierDetailDTO> rectifiers;

    public static RectifierDetailResDTO toRes(
        Facility facility,
        List<RectifierDetailDTO> rectifiers
    ) {
        return RectifierDetailResDTO.builder()
            .facilityId(facility.getFacilityId())
            .mngNo(facility.getMngNo())
            .facilityName(facility.getFacilityName())
            .rectifiers(rectifiers)
            .build();
    }
}