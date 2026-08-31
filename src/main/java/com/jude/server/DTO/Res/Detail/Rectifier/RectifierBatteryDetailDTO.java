package com.jude.server.DTO.Res.Detail.Rectifier;

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
public class RectifierBatteryDetailDTO {

    private Long batId;

    private Long batCapaAh;
    private Long batVoltageV;
    private Long quantity;

    private String batModel;

    private LocalDate installDate;
    private LocalDate nextExchangeDate;

    public static RectifierBatteryDetailDTO toRes(
        BAT bat
    ) {

        LocalDate installDate =
            bat.getInstallDate();

        return RectifierBatteryDetailDTO.builder()
            .batId(
                bat.getBatId()
            )
            .batCapaAh(
                bat.getBatCapaAh()
            )
            .batVoltageV(
                bat.getBatVoltageV()
            )
            .quantity(
                bat.getQuantity()
            )
            .batModel(
                bat.getBatModel()
            )
            .installDate(
                installDate
            )
            .nextExchangeDate(
                installDate == null
                    ? null
                    : installDate.plusYears(3)
            )
            .build();
    }
}