package com.jude.server.DTO.Res.Pagenation.Inspection;

import com.jude.server.DTO.Enum.InspectionType;
import com.jude.server.Entity.Inspection.Inspection;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InspectionInfoDTO {

    private InspectionType inspectionType;
    private LocalDate completeDate;
    private LocalDate dueDate;


    public static InspectionInfoDTO toRes(
        InspectionType type,
        Inspection inspection
    ) {
        LocalDate completeDate =
            inspection == null
                ? null
                : inspection.getCompleteDate();

        LocalDate nextInspectionDate =
            completeDate == null
                ? null
                : completeDate.plusYears(3);


        return InspectionInfoDTO.builder()
            .inspectionType(type)
            .completeDate(completeDate)
            .dueDate(nextInspectionDate)
            .build();

    }
}
