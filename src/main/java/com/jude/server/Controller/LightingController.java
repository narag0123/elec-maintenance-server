package com.jude.server.Controller;

import com.jude.server.DTO.Res.Detail.Lighting.LightingDetailResDTO;
import com.jude.server.DTO.Res.Detail.Lighting.LightingUpdateReqDTO;
import com.jude.server.DTO.Res.Pagenation.Lighting.LightingResDTO;
import com.jude.server.Service.LightingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lighting")
public class LightingController {

    private final LightingService lightingService;

    @GetMapping("/get")
    public ResponseEntity<List<LightingResDTO>> getAll() {
        return ResponseEntity.ok(
            lightingService.getAll()
        );
    }

    @GetMapping("/{facilityId}")
    public ResponseEntity<LightingResDTO> getByFacility(
        @PathVariable Long facilityId
    ) {

        return ResponseEntity.ok(
            lightingService.getByFacility(
                facilityId
            )
        );
    }

    @GetMapping("/detail/{facilityId}")
    public ResponseEntity<LightingDetailResDTO> detailByFacility(
        @PathVariable Long facilityId
    ){
        return ResponseEntity.ok(lightingService.detail(facilityId)
        );
    }

    @PatchMapping("/update/{facilityId}")
    public ResponseEntity<LightingDetailResDTO> update(
        @PathVariable Long facilityId,
        @RequestBody LightingUpdateReqDTO req
    ) {

        return ResponseEntity.ok(
            lightingService.update(
                facilityId,
                req
            )
        );
    }
}