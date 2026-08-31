package com.jude.server.Service;

import com.jude.server.DTO.Enum.EmergencyEquipmentType;
import com.jude.server.DTO.Req.Create.EmergencyEquipmentCreateReqDTO;
import com.jude.server.DTO.Res.Create.EmergencyEquipmentCreateResDTO;
import com.jude.server.Entity.Equipment.EmergencyEquipment;
import com.jude.server.Entity.Room;
import com.jude.server.Repository.EmergencyEquipmentRepository;
import com.jude.server.Repository.GenRepository;
import com.jude.server.Repository.RoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class EmergencyEquipmentService {

    private final EmergencyEquipmentRepository emergencyEquipmentRepository;
    private final RoomRepository roomRepository;
    private final GenRepository genRepository;

    private final GenService genService;
    private final UPSService upsService;
    private final RecService recService;


//    public EmergencyEquipmentCreateResDTO EmergencyEquipmentCreate(
//        EmergencyEquipmentCreateReqDTO dto
//    ) {
//        Room roomEntity = roomRepository.findById(dto.getRoomId())
//            .orElseThrow(() -> new IllegalArgumentException("Room ID 없음"));
//
//        // 비상설비 생성
//        EmergencyEquipment entity = EmergencyEquipmentCreateReqDTO.toEntity(dto, roomEntity);
//        EmergencyEquipment saved = emergencyEquipmentRepository.save(entity);
//
//        // 비상장비 타입확인하여 Data 생성
//        EmergencyEquipmentType eType = dto.getEmergencyEquipmentType();
//        switch (eType) {
//            case 발전기 -> genService.genCreateReq(dto.getGen(), saved);
//            case UPS -> upsService.upsCreateReq(dto.getUps(), saved);
//            case 정류기 -> recService.recCreateReq(dto.getRec(), saved);
//        }
//
//        return EmergencyEquipmentCreateResDTO.toRes(saved, eType);
//    }

}
