package com.jude.server.DTO.Res.Detail.Inspection;

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
public class InspectionInfoDetailDTO {

    private InspectionType inspectionType;
    private LocalDate completeDate;
    private LocalDate dueDate;

    public static InspectionInfoDetailDTO toRes(
        InspectionType inspectionType,
        Inspection inspection
    ) {

        LocalDate completeDate =
            inspection == null
                ? null
                : inspection.getCompleteDate();

        LocalDate dueDate =
            completeDate == null
                ? null
                : completeDate.plusYears(3);

        return InspectionInfoDetailDTO.builder()
            .inspectionType(inspectionType)
            .completeDate(completeDate)
            .dueDate(dueDate)
            .build();
    }
}