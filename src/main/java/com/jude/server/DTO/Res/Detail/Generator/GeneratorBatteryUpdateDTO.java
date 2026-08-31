package com.jude.server.DTO.Res.Detail.Generator;

import com.jude.server.Entity.Equipment.BAT;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorBatteryUpdateDTO {

    private Long batId;

    private Long batCapaAh;
    private Long batVoltageV;
    private Long quantity;

    private String batModel;

    private LocalDate installDate;

    public void updateEntity(
        BAT bat
    ) {

        bat.setBatCapaAh(
            batCapaAh
        );

        bat.setBatVoltageV(
            batVoltageV
        );

        bat.setQuantity(
            quantity
        );

        bat.setBatModel(
            batModel
        );

        bat.setInstallDate(
            installDate
        );
    }
}