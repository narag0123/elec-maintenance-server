package com.jude.server.Controller;

import com.jude.server.DTO.Res.Detail.Inspection.InspectionDetailResDTO;
import com.jude.server.DTO.Res.Detail.Inspection.InspectionUpdateRequestDTO;
import com.jude.server.DTO.Res.Pagenation.Inspection.InspectionPageResDTO;
import com.jude.server.DTO.Res.Pagenation.PageResponse;
import com.jude.server.Service.InspectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/inspection")
public class InspectionController {

    private final InspectionService inspectionService;

    @GetMapping("/get")
    public ResponseEntity<PageResponse<InspectionPageResDTO>> inspectionPage(
        @PageableDefault(
              size = 40,
              sort = "facilityId",
              direction = Sort.Direction.ASC
          ) Pageable pageable
    ) {
        return ResponseEntity.ok(
            inspectionService.inspectionPage(pageable)
        );
    }



    @GetMapping("/detail/{facilityId}")
    public ResponseEntity<InspectionDetailResDTO> detail(
        @PathVariable Long facilityId
    ) {

        return ResponseEntity.ok(
            inspectionService.detail(
                facilityId
            )
        );
    }

    @PutMapping("/update/{facilityId}")
    public ResponseEntity<Void> update(
        @PathVariable Long facilityId,
        @RequestBody InspectionUpdateRequestDTO request
    ) {

        inspectionService.update(
            facilityId,
            request
        );

        return ResponseEntity
            .noContent()
            .build();
    }
}
