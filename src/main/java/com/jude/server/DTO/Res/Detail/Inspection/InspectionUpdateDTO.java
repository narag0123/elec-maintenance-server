package com.jude.server.DTO.Res.Detail.Inspection;

import com.jude.server.DTO.Enum.InspectionType;
import com.jude.server.Entity.Inspection.Inspection;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InspectionUpdateDTO {

    private InspectionType inspectionType;
    private LocalDate completeDate;

    public void updateEntity(
        Inspection inspection
    ) {
        inspection.setCompleteDate(
            completeDate
        );
    }
}