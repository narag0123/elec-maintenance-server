package com.jude.server.DTO.Res.Detail.Generator;

import com.jude.server.DTO.Enum.GenType;
import com.jude.server.Entity.Equipment.EmergencyEquipment;
import com.jude.server.Entity.Equipment.Gen;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorUpdateDTO {

    private Long emergencyEquipmentId;
    private Long genId;

    private String manufacturer;
    private LocalDate installDate;
    private String model;

    private Long genVoltageV;
    private Long genCapaKva;
    private GenType genType;

    private LocalDate lastConsumableExchangeDate;

    /*
     * 화면에서는 내려주지만
     * 실제 UPDATE에서는 사용하지 않음.
     *
     * lastConsumableExchangeDate + 3년으로
     * 서버에서 계산되는 값.
     */
    private LocalDate nextConsumableExchangeDate;

    private List<GeneratorBatteryUpdateDTO> batteries;

    /*
     * EmergencyEquipment 수정
     */
    public void updateEmergencyEquipment(
        EmergencyEquipment emergencyEquipment
    ) {
        emergencyEquipment.setManufacturer(
            manufacturer
        );

        emergencyEquipment.setInstallDate(
            installDate
        );

        emergencyEquipment.setModel(
            model
        );
    }

    /*
     * Gen 수정
     */
    public void updateGen(
        Gen gen
    ) {
        gen.setGenVoltageV(
            genVoltageV
        );

        gen.setGenCapaKva(
            genCapaKva
        );

        gen.setGenType(
            genType
        );

        gen.setLastConsumableExchangeDate(
            lastConsumableExchangeDate
        );
    }
}