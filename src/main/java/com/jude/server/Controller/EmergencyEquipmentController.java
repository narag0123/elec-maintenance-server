package com.jude.server.Controller;


import com.jude.server.DTO.Req.Create.EmergencyEquipmentCreateReqDTO;
import com.jude.server.DTO.Res.Create.EmergencyEquipmentCreateResDTO;
import com.jude.server.Service.EmergencyEquipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emergency-equipment")
public class EmergencyEquipmentController {

    private final EmergencyEquipmentService emergencyEquipmentService;

//    @PostMapping("/create")
//    public ResponseEntity<EmergencyEquipmentCreateResDTO> EmergencyEquipmentCreate(
//       @RequestBody EmergencyEquipmentCreateReqDTO dto
//    ) {
//        EmergencyEquipmentCreateResDTO res = emergencyEquipmentService.EmergencyEquipmentCreate(
//            dto);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(res);
//    }
}
