package com.jude.server.Controller;


import com.jude.server.DTO.Req.Create.PowerReceivingCreateReqDTO;
import com.jude.server.DTO.Res.Create.PowerReceivingCreateResDTO;
import com.jude.server.DTO.Res.Detail.PowerReceiving.PowerReceivingDetailResDTO;
import com.jude.server.DTO.Res.Detail.PowerReceiving.PowerReceivingUpdateReqDTO;
import com.jude.server.DTO.Res.Pagenation.PageResponse;
import com.jude.server.DTO.Res.Pagenation.PowerRecieving.PowerReceivingByFacilityPageResDTO;
import com.jude.server.Service.PowerReceivingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/power-receiving")
@CrossOrigin(origins = "http://localhost:3000")
public class PowerReceivingController {

    private final PowerReceivingService powerReceivingService;

    @PostMapping("/create")
    public ResponseEntity<PowerReceivingCreateResDTO> powerReceivingCreateReq(
       @RequestBody PowerReceivingCreateReqDTO dto
    ) {
        PowerReceivingCreateResDTO res = powerReceivingService.powerReceivingCreateReq(
            dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }


    @GetMapping("/get")
    public PageResponse<PowerReceivingByFacilityPageResDTO> pagePowerReceiving(
        @PageableDefault(
            size = 40,
            sort = "facilityId",
            direction = Sort.Direction.ASC
        ) Pageable pageable
    ) {
        return powerReceivingService.powerReceivingByFacility(pageable);
    }

    @GetMapping("/detail/{facilityId}")
    public ResponseEntity<PowerReceivingDetailResDTO> detail(
        @PathVariable Long facilityId
    ) {
        return ResponseEntity.ok(
            powerReceivingService.detail(
                facilityId
            )
        );
    }

    @PutMapping("/update/{facilityId}")
    public ResponseEntity<Void> update(
        @PathVariable Long facilityId,
        @RequestBody PowerReceivingUpdateReqDTO request
    ) {
        powerReceivingService.update(facilityId, request);

        return ResponseEntity.noContent().build();

    }
}
