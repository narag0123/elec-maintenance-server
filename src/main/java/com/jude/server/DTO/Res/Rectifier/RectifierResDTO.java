package com.jude.server.DTO.Res.Rectifier;

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
public class RectifierResDTO {

    private Long facilityId;
    private String mngNo;
    private String facilityName;

    private List<RectifierInfoDTO> rectifiers;

    public static RectifierResDTO toRes(
        Facility facility,
        List<RectifierInfoDTO> rectifiers
    ) {
        return RectifierResDTO.builder()
            .facilityId(
                facility.getFacilityId()
            )
            .mngNo(
                facility.getMngNo()
            )
            .facilityName(
                facility.getFacilityName()
            )
            .rectifiers(
                rectifiers
            )
            .build();
    }
}