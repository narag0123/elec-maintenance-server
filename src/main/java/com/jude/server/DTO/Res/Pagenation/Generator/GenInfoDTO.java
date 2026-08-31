package com.jude.server.DTO.Res.Pagenation.Generator;

import com.jude.server.DTO.Enum.GenType;
import com.jude.server.Entity.Equipment.Gen;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenInfoDTO {

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

    private List<GenBatteryDTO> batteries;

    public static GenInfoDTO toRes(
        Gen gen,
        List<GenBatteryDTO> batteries
    ) {

        LocalDate next =
            gen.getLastConsumableExchangeDate() == null
                ? null
                : gen.getLastConsumableExchangeDate().plusYears(3);

        return GenInfoDTO.builder()
            .emergencyEquipmentId(
                gen.getEmergencyEquipment().getEmergencyEquipmentId()
            )
            .genId(gen.getGenId())
            .manufacturer(gen.getEmergencyEquipment().getManufacturer())
            .installDate(gen.getEmergencyEquipment().getInstallDate())
            .genType(gen.getGenType())
            .model(gen.getEmergencyEquipment().getModel())
            .genVoltageV(gen.getGenVoltageV())
            .genCapaKva(gen.getGenCapaKva())
            .lastConsumableExchangeDate(
                gen.getLastConsumableExchangeDate()
            )
            .nextConsumableExchangeDate(next)
            .batteries(batteries)
            .build();
    }
}