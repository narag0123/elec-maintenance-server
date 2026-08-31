package com.jude.server.DTO.Res.Create;

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
public class FacilityCreateResDTO {
    private Long facilityId;

    public static FacilityCreateResDTO toRes(
        Facility entity
    ){
        return FacilityCreateResDTO.builder()
            .facilityId(entity.getFacilityId())
            .build();
    }
}
