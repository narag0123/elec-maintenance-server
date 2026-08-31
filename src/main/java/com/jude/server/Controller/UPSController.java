package com.jude.server.Controller;

import com.jude.server.DTO.Res.Detail.UPS.UPSDetailResDTO;
import com.jude.server.DTO.Res.Detail.UPS.UPSUpdateReqDTO;
import com.jude.server.DTO.Res.Pagenation.PageResponse;
import com.jude.server.DTO.Res.Pagenation.Ups.UPSPageResDTO;
import com.jude.server.Service.UPSService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ups")
@RequiredArgsConstructor
public class UPSController {

    private final UPSService upsService;

    @GetMapping("/get")
    public ResponseEntity<PageResponse<UPSPageResDTO>> page(
        Pageable pageable
    ) {
        return ResponseEntity.ok(
            upsService.page(pageable)
        );
    }

    @GetMapping("/detail/{facilityId}")
    public ResponseEntity<UPSDetailResDTO> detail(
        @PathVariable Long facilityId
    ) {
        return ResponseEntity.ok(
            upsService.detail(
                facilityId
            )
        );
    }

    @PutMapping("/update/{facilityId}")
    public ResponseEntity<Void> update(
        @PathVariable Long facilityId,
        @RequestBody UPSUpdateReqDTO request
    ) {
        upsService.update(
            facilityId,
            request
        );

        return ResponseEntity
            .noContent()
            .build();
    }
}
