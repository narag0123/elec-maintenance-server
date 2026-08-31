package com.jude.server.Service;

import com.jude.server.DTO.Req.Create.EmergencyEquipmentSubDTOs.BatCreateReqDTO;
import com.jude.server.DTO.Res.Create.BatCreateResDTO;
import com.jude.server.Entity.Equipment.BAT;
import com.jude.server.Entity.Equipment.EmergencyEquipment;
import com.jude.server.Repository.BATRepository;
import com.jude.server.Repository.EmergencyEquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BatService {
    private final BATRepository batRepository;
    private final EmergencyEquipmentRepository emergencyEquipmentRepository;

    public BatCreateResDTO batCreateReq(
        BatCreateReqDTO dto
    ){

        EmergencyEquipment emergencyEquipment = emergencyEquipmentRepository.findById(
                dto.getEmergencyEquipmentId())
            .orElseThrow(() -> new IllegalArgumentException("Emergency Equipment ID 없음"));

        BAT bat = BatCreateReqDTO.toEntity(dto, emergencyEquipment);
        BAT saved = batRepository.save(bat);

        return BatCreateResDTO.toRes(saved);
    }
}
