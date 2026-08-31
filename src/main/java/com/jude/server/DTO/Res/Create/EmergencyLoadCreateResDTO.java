package com.jude.server.DTO.Res.Create;

import com.jude.server.Entity.Equipment.EmergencyLoad;
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
public class EmergencyLoadCreateResDTO {
    private Long emergencyLoadId;

    public static EmergencyLoadCreateResDTO toRes(
        EmergencyLoad e
    ){
        return EmergencyLoadCreateResDTO.builder()
            .emergencyLoadId(e.getEmergencyLoadId())
            .build();
    }
}
