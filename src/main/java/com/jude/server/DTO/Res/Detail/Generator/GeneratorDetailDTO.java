package com.jude.server.DTO.Res.Detail.Generator;

import com.jude.server.DTO.Enum.GenType;
import com.jude.server.Entity.Equipment.Gen;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeneratorDetailDTO {

    private Long emergencyEquipmentId;
    private Long genId;

    private String manufacturer;
    private LocalDate installDate;
    private String model;

    private Long genVoltageV;
    private Long genCapaKva;
    private GenType genType;

    private LocalDate lastConsumableExchangeDate;
    private LocalDate nextConsumableExchangeDate;

    private List<GeneratorBatteryDetailDTO> batteries;

    public static GeneratorDetailDTO toRes(
        Gen gen,
        List<GeneratorBatteryDetailDTO> batteries
    ) {

        LocalDate lastExchangeDate =
            gen.getLastConsumableExchangeDate();

        LocalDate nextExchangeDate =
            lastExchangeDate == null
                ? null
                : lastExchangeDate.plusYears(3);

        return GeneratorDetailDTO.builder()
            .emergencyEquipmentId(
                gen.getEmergencyEquipment()
                    .getEmergencyEquipmentId()
            )
            .genId(gen.getGenId())

            .manufacturer(
                gen.getEmergencyEquipment()
                    .getManufacturer()
            )
            .installDate(
                gen.getEmergencyEquipment()
                    .getInstallDate()
            )
            .model(
                gen.getEmergencyEquipment()
                    .getModel()
            )

            .genVoltageV(gen.getGenVoltageV())
            .genCapaKva(gen.getGenCapaKva())
            .genType(gen.getGenType())

            .lastConsumableExchangeDate(
                lastExchangeDate
            )
            .nextConsumableExchangeDate(
                nextExchangeDate
            )

            .batteries(batteries)

            .build();
    }
}