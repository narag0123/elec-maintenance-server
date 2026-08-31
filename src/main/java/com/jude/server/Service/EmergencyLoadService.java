package com.jude.server.Service;

import com.jude.server.DTO.Req.Create.EmergencyEquipmentSubDTOs.EmergencyLoadCreateReqDTO;
import com.jude.server.DTO.Res.Create.EmergencyLoadCreateResDTO;
import com.jude.server.Entity.Equipment.EmergencyEquipment;
import com.jude.server.Entity.Equipment.EmergencyLoad;
import com.jude.server.Repository.EmergencyEquipmentRepository;
import com.jude.server.Repository.EmergencyLoadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmergencyLoadService {
    private final EmergencyLoadRepository emergencyLoadRepository;
    private final EmergencyEquipmentRepository emergencyEquipmentRepository;

    public EmergencyLoadCreateResDTO emergencyLoadCreateReq(
        EmergencyLoadCreateReqDTO dto
    ){
        EmergencyEquipment emergencyEquipment = emergencyEquipmentRepository.findById(
                dto.getEmergencyEquipmentId())
            .orElseThrow(() -> new IllegalArgumentException("Emergency Equipment ID 없음"));

        EmergencyLoad emergencyLoad = EmergencyLoadCreateReqDTO.toEntity(dto, emergencyEquipment);
        EmergencyLoad saved = emergencyLoadRepository.save(emergencyLoad);

        return EmergencyLoadCreateResDTO.toRes(saved);

    }

}
