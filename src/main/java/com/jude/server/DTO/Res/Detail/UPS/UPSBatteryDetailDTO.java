package com.jude.server.DTO.Res.Detail.UPS;

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
public class UPSBatteryDetailDTO {

    private Long batId;

    private LocalDate installDate;
    private LocalDate nextExchangeDate;

    private Long batVoltageV;
    private Long batCapaAh;
    private Long quantity;
    private Long totalCapaKwh;
    private String batModel;

    public static UPSBatteryDetailDTO toRes(
        BAT bat
    ) {

        LocalDate installDate =
            bat.getInstallDate();

        LocalDate nextExchangeDate =
            installDate == null
                ? null
                : installDate.plusYears(3);

        return UPSBatteryDetailDTO.builder()
            .batId(
                bat.getBatId()
            )
            .installDate(
                installDate
            )
            .nextExchangeDate(
                nextExchangeDate
            )
            .batVoltageV(
                bat.getBatVoltageV()
            )
            .batCapaAh(
                bat.getBatCapaAh()
            )
            .quantity(
                bat.getQuantity()
            )
            .totalCapaKwh(
                bat.getTotalCapaKwh()
            )
            .batModel(
                bat.getBatModel()
            )
            .build();
    }
}