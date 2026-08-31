package com.jude.server.DTO.Res.Manager;

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
public class ElecManagerResDTO {
    private Long facilityId;
    private String mngNo;
    private String facilityName;

    private Long powerReceivingKw;
    private Long generatorTotalKva;

    private List<ElecManagerInfoDTO> managers;

    public static ElecManagerResDTO toRes(
        Facility facility,
        Long powerReceivingKw,
        Long generatorTotalKva,
        List<ElecManagerInfoDTO> managers
    ) {
        return ElecManagerResDTO.builder()
            .facilityId(facility.getFacilityId())
            .mngNo(facility.getMngNo())
            .facilityName(facility.getFacilityName())
            .powerReceivingKw(powerReceivingKw)
            .generatorTotalKva(generatorTotalKva)
            .managers(managers)
            .build();
    }
}