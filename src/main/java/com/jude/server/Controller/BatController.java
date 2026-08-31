package com.jude.server.Controller;

import com.jude.server.DTO.Req.Create.EmergencyEquipmentSubDTOs.BatCreateReqDTO;
import com.jude.server.DTO.Res.Create.BatCreateResDTO;
import com.jude.server.Service.BatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/emergency-equipment/bat")
public class BatController {

    private final BatService batService;

    @PostMapping("/create")
    public ResponseEntity<BatCreateResDTO> batCreateReq(
        @RequestBody BatCreateReqDTO dto
    ){
        BatCreateResDTO res = batService.batCreateReq(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }
}
