package com.jude.server.DTO.Res.Detail.Inspection;

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
public class InspectionDetailResDTO {

    private Long facilityId;
    private String mngNo;
    private String facilityName;

    private RoomInspectionDetailDTO room;
    private String customerNo;

    private List<GenInspectionDetailDTO> gens;
    private List<UPSInspectionDetailDTO> ups;
    private List<InspectionInfoDetailDTO> inspections;

    public static InspectionDetailResDTO toRes(
        Facility facility,
        RoomInspectionDetailDTO room,
        List<GenInspectionDetailDTO> gens,
        List<UPSInspectionDetailDTO> ups,
        List<InspectionInfoDetailDTO> inspections
    ) {
        return InspectionDetailResDTO.builder()
            .facilityId(facility.getFacilityId())
            .mngNo(facility.getMngNo())
            .facilityName(facility.getFacilityName())
            .room(room)
            .customerNo(facility.getCustomerNo())
            .gens(gens)
            .ups(ups)
            .inspections(inspections)
            .build();
    }
}