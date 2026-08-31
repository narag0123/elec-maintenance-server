package com.jude.server.DTO.Res.Detail.Inspection;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InspectionUpdateRequestDTO {

    private List<InspectionUpdateDTO> inspections;
}