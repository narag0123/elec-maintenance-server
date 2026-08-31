package com.jude.server.DTO.Res.Detail.UPS;

import com.jude.server.Entity.Equipment.BAT;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UPSBatteryUpdateDTO {

    private Long batId;

    private LocalDate installDate;

    /*
     * 프론트에서 오기는 하지만
     * 실제 DB 수정에는 사용하지 않음.
     *
     * installDate + 3년으로 계산되는 값.
     */
    private LocalDate nextExchangeDate;

    private Long batVoltageV;
    private Long batCapaAh;
    private Long quantity;

    private Long totalCapaKwh;

    private String batModel;

    public void updateEntity(
        BAT bat
    ) {
        bat.setInstallDate(
            installDate
        );

        bat.setBatVoltageV(
            batVoltageV
        );

        bat.setBatCapaAh(
            batCapaAh
        );

        bat.setQuantity(
            quantity
        );

        bat.setTotalCapaKwh(
            totalCapaKwh
        );

        bat.setBatModel(
            batModel
        );
    }
}
