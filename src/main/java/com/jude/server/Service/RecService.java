package com.jude.server.Service;

import com.jude.server.DTO.Req.Create.EmergencyEquipmentSubDTOs.RecCreateReqDTO;
import com.jude.server.Entity.Equipment.EmergencyEquipment;
import com.jude.server.Entity.Equipment.Rectifier;
import com.jude.server.Repository.RecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecService {

    private final RecRepository recRepository;

    public void recCreateReq(
        RecCreateReqDTO dto,
        EmergencyEquipment emergencyEquipment
    ){
        Rectifier rectifier = RecCreateReqDTO.toEntity(dto, emergencyEquipment);
        recRepository.save(rectifier);
    }
}
