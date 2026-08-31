package com.jude.server.Controller;

import com.jude.server.DTO.Res.Manager.ElecManagerResDTO;
import com.jude.server.Service.ElecManagerService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/elec-managers")
public class ElecManagerController {

    private final ElecManagerService elecManagerService;

    @GetMapping("/get")
    public ResponseEntity<List<ElecManagerResDTO>> getAll() {
        return ResponseEntity.ok(
            elecManagerService.getAll()
        );
    }
}