package com.jude.server.DTO.Res.Detail.Generator;


import com.jude.server.Entity.Equipment.BAT;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorBatteryDetailDTO {

    private Long batId;

    private Long batCapaAh;
    private Long batVoltageV;
    private Long quantity;

    private String batModel;

    private LocalDate installDate;

    public static GeneratorBatteryDetailDTO toRes(
        BAT bat
    ) {
        return GeneratorBatteryDetailDTO.builder()
            .batId(bat.getBatId())
            .batCapaAh(bat.getBatCapaAh())
            .batVoltageV(bat.getBatVoltageV())
            .quantity(bat.getQuantity())
            .batModel(bat.getBatModel())
            .installDate(bat.getInstallDate())
            .build();
    }
}
