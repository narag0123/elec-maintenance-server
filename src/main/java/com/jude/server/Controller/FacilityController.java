package com.jude.server.Controller;

import com.jude.server.DTO.Req.Create.FacilityCreateReqDTO;
import com.jude.server.DTO.Res.Create.FacilityCreateResDTO;
import com.jude.server.Service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/facility")
public class FacilityController {

    private final FacilityService facilityService;

    @PostMapping("/create")
    public ResponseEntity<FacilityCreateResDTO> createFacility(
        @RequestBody FacilityCreateReqDTO dto
    ){
        FacilityCreateResDTO res = facilityService.facilityCreateReq(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }


}
