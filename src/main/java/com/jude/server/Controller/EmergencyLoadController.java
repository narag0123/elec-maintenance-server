package com.jude.server.Controller;

import com.jude.server.DTO.Req.Create.EmergencyEquipmentSubDTOs.EmergencyLoadCreateReqDTO;
import com.jude.server.DTO.Res.Create.EmergencyLoadCreateResDTO;
import com.jude.server.Service.EmergencyLoadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emergency-equipment/emergency-load")
public class EmergencyLoadController {

    private final EmergencyLoadService emergencyLoadService;

    @PostMapping("/create")
    public ResponseEntity<EmergencyLoadCreateResDTO> emergencyLoadCreateReq(
        @RequestBody EmergencyLoadCreateReqDTO dto
    ) {
        EmergencyLoadCreateResDTO res = emergencyLoadService.emergencyLoadCreateReq(
            dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
