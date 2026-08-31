package com.jude.server.DTO.Res.Pagenation.Generator;

import com.jude.server.Entity.Equipment.BAT;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenBatteryDTO {

    private Long batId;

    private Long batCapaAh;
    private Long batVoltageV;
    private Long quantity;

    private String batModel;
    private LocalDate installDate;

    public static GenBatteryDTO toRes(BAT bat) {
        return GenBatteryDTO.builder()
            .batId(bat.getBatId())
            .batCapaAh(bat.getBatCapaAh())
            .batVoltageV(bat.getBatVoltageV())
            .quantity(bat.getQuantity())
            .batModel(bat.getBatModel())
            .installDate(bat.getInstallDate())
            .build();
    }
}