package com.jude.server.DTO.Res.Pagenation.Inspection;

import com.jude.server.DTO.Enum.RoomType;
import com.jude.server.Entity.Facility;
import com.jude.server.Entity.PowerReceiving;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InspectionPageResDTO {

    private Long facilityId;
    private String mngNo;
    private String facilityName;

    private RoomInspectionDTO room;
    private String customerNo;

    private List<GenInspectionDTO> gens;
    private List<UPSInspectionDTO> ups;
    private List<InspectionInfoDTO> inspections;

    public static InspectionPageResDTO toRes(
        Facility facility,
        RoomInspectionDTO room,
        List<GenInspectionDTO> gens,
        List<UPSInspectionDTO> ups,
        List<InspectionInfoDTO> inspections
    ) {

        return InspectionPageResDTO.builder()
            .facilityId(facility.getFacilityId())
            .mngNo(facility.getMngNo())
            .facilityName(facility.getFacilityName())
            .customerNo(facility.getCustomerNo())
            .room(room)
            .gens(gens)
            .ups(ups)
            .inspections(inspections)
            .build();
    }
}