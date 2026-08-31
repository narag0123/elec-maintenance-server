package com.jude.server.DTO.Req.Create;

import com.jude.server.DTO.Enum.EmergencyEquipmentType;
import com.jude.server.DTO.Req.Create.EmergencyEquipmentSubDTOs.GenCreateReqDTO;
import com.jude.server.DTO.Req.Create.EmergencyEquipmentSubDTOs.RecCreateReqDTO;
import com.jude.server.DTO.Req.Create.EmergencyEquipmentSubDTOs.UPSCreateReqDTO;
import com.jude.server.Entity.Equipment.EmergencyEquipment;
import com.jude.server.Entity.Room;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class EmergencyEquipmentCreateReqDTO {

    private String manufacturer;
    private LocalDate installDate;
    private String model;
    private EmergencyEquipmentType emergencyEquipmentType;
    private String cubicleNo;

    private GenCreateReqDTO gen;
    private UPSCreateReqDTO ups;
    private RecCreateReqDTO rec;

    private Long roomId;

    public static EmergencyEquipment toEntity(
        EmergencyEquipmentCreateReqDTO dto,
        Room roomEntity
    ) {
        return EmergencyEquipment.builder()
            .manufacturer(dto.getManufacturer())
            .installDate(dto.getInstallDate())
            .model(dto.getModel())
            .emergencyEquipmentType(dto.getEmergencyEquipmentType())
            .cubicleNo(dto.getCubicleNo())
            .room(roomEntity)
            .build();
    }


}
