package com.jude.server.Controller;

import com.jude.server.DTO.Res.Detail.Rectifier.RectifierDetailResDTO;
import com.jude.server.DTO.Res.Detail.Rectifier.RectifierUpdateReqDTO;
import com.jude.server.DTO.Res.Rectifier.RectifierResDTO;
import com.jude.server.Service.RectifierService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rectifier")
public class RectifierController {

    private final RectifierService rectifierService;

    @GetMapping("/get")
    public ResponseEntity<List<RectifierResDTO>> getAll() {

        return ResponseEntity.ok(
            rectifierService.getAll()
        );
    }


    @GetMapping("/detail/{facilityId}")
    public ResponseEntity<RectifierDetailResDTO> detail(
        @PathVariable Long facilityId
    ) {

        return ResponseEntity.ok(
            rectifierService.detail(
                facilityId
            )
        );
    }

    @PutMapping("/update/{facilityId}")
    public ResponseEntity<Void> update(
        @PathVariable Long facilityId,
        @RequestBody RectifierUpdateReqDTO request
    ) {

        rectifierService.update(
            facilityId,
            request
        );

        return ResponseEntity
            .noContent()
            .build();
    }
}