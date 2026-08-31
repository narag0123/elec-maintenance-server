package com.jude.server.DTO.Req.Create;


import com.jude.server.DTO.Enum.FacilityType;
import com.jude.server.Entity.Facility;
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
public class FacilityCreateReqDTO {
    private String facilityName;
    private FacilityType facilityType;
    private String mngNo;

    public static Facility toEntity(
        FacilityCreateReqDTO dto
    ){
        return Facility.builder()
            .facilityName(dto.getFacilityName())
            .facilityType(dto.getFacilityType())
            .mngNo(dto.getMngNo())
            .build();
    }
}
