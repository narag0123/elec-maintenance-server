package com.jude.server.Controller;


import com.jude.server.DTO.Req.Create.RoomCreateReqDTO;
import com.jude.server.DTO.Res.Create.RoomCreateResDTO;
import com.jude.server.Service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/room")
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/create")
    public ResponseEntity<RoomCreateResDTO> roomCreateReq(
        @RequestBody RoomCreateReqDTO dto
    ) {
        System.out.println("dto: " + dto);
        RoomCreateResDTO res = roomService.roomCreateReq(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(res);
    }

}
