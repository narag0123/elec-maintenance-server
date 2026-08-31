package com.jude.server.DTO.Res.Detail.Rectifier;



import com.jude.server.Entity.Equipment.BAT;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RectifierBatteryUpdateDTO {

    private Long batId;

    private Long batCapaAh;
    private Long batVoltageV;
    private Long quantity;

    private String batModel;

    private LocalDate installDate;

    /*
     * 프론트에서는 보내지만
     * DB 수정에는 사용하지 않음
     */
    private LocalDate nextExchangeDate;

    public void updateEntity(
        BAT bat
    ) {
        bat.setBatCapaAh(batCapaAh);
        bat.setBatVoltageV(batVoltageV);
        bat.setQuantity(quantity);
        bat.setBatModel(batModel);
        bat.setInstallDate(installDate);
    }
}