package com.jude.server.DTO.Req.Create.EmergencyEquipmentSubDTOs;

import com.jude.server.DTO.Enum.EmergencyEquipmentType;
import com.jude.server.Entity.Equipment.BAT;
import com.jude.server.Entity.Equipment.EmergencyEquipment;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.antlr.v4.runtime.misc.NotNull;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class BatCreateReqDTO {
    private Long quantity;
    private Long batCapaAh;
    private Long batVoltageV;
    private LocalDate installDate;

    private Long emergencyEquipmentId;

    public static BAT toEntity(
        BatCreateReqDTO dto,
        EmergencyEquipment emergencyEquipment
    ){
        return BAT.builder()
            .quantity(dto.getQuantity())
            .batCapaAh(dto.getBatCapaAh())
            .batVoltageV(dto.getBatVoltageV())
            .installDate(dto.getInstallDate())
            .emergencyEquipment(emergencyEquipment)
            .build();
    }
}
