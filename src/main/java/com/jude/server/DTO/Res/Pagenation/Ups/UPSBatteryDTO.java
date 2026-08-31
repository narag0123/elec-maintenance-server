package com.jude.server.DTO.Res.Pagenation.Ups;

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
public class UPSBatteryDTO {

    private Long batId;

    private LocalDate installDate;
    private LocalDate nextExchangeDate;

    private Long batVoltageV;
    private Long batCapaAh;
    private Long quantity;

    private Long totalCapaKwh;
    private String batModel;

    public static UPSBatteryDTO toRes(
        BAT bat
    ) {
        LocalDate installDate = bat.getInstallDate();

        return UPSBatteryDTO.builder()
            .batId(bat.getBatId())
            .installDate(installDate)
            .nextExchangeDate(
                installDate == null
                    ? null
                    : installDate.plusYears(3)
            )
            .batVoltageV(bat.getBatVoltageV())
            .batCapaAh(bat.getBatCapaAh())
            .quantity(bat.getQuantity())
            .totalCapaKwh(bat.getTotalCapaKwh())
            .batModel(bat.getBatModel())
            .build();
    }
}