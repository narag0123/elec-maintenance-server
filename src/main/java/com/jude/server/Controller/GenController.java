package com.jude.server.Controller;

import com.jude.server.DTO.Res.Detail.Generator.GeneratorDetailResDTO;
import com.jude.server.DTO.Res.Detail.Generator.GeneratorUpdateReqDTO;
import com.jude.server.DTO.Res.Pagenation.Generator.GenPageResDTO;
import com.jude.server.DTO.Res.Pagenation.PageResponse;
import com.jude.server.Service.GenService;
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
@RequiredArgsConstructor
@RequestMapping("/generator")
public class GenController {

    private final GenService genService;

    @GetMapping("/get")
    public ResponseEntity<PageResponse<GenPageResDTO>> genPage(
        Pageable pageable
    ) {
        return ResponseEntity.ok(
            genService.page(pageable)
        );
    }

    @GetMapping("/detail/{facilityId}")
    public ResponseEntity<GeneratorDetailResDTO> detail(
        @PathVariable Long facilityId
    ) {
        return ResponseEntity.ok(genService.detail(facilityId));
    }

    @PutMapping("/update/{facilityId}")
    public ResponseEntity<Void> update(
        @PathVariable Long facilityId,
        @RequestBody GeneratorUpdateReqDTO request
    ) {
        genService.update(facilityId, request);

        return ResponseEntity
            .noContent()
            .build();
    }
}