package com.jude.server.DTO.Res.Manager;

import com.jude.server.DTO.Enum.ElecManagerType;
import com.jude.server.Entity.Inspection.ElecManager;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElecManagerInfoDTO {
    private Long elecManagerId;
    private ElecManagerType elecManagerType;
    private String managerName;
    private LocalDate effectiveDate;
    private String certificate;

    public static ElecManagerInfoDTO toRes(
        ElecManager elecManager
    ) {
        return ElecManagerInfoDTO.builder()
            .elecManagerId(elecManager.getElecManagerId())
            .elecManagerType(elecManager.getElecManagerType())
            .managerName(elecManager.getManagerName())
            .effectiveDate(elecManager.getEffectiveDate())
            .certificate(elecManager.getCertificate())
            .build();
    }
}