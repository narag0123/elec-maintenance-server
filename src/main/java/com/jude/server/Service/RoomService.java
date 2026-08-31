package com.jude.server.Service;

import com.jude.server.DTO.Req.Create.RoomCreateReqDTO;
import com.jude.server.DTO.Res.Create.RoomCreateResDTO;
import com.jude.server.DTO.Res.Pagenation.PowerRecieving.PowerReceivingPageResDTO;
import com.jude.server.DTO.Res.Pagenation.PowerRecieving.RoomPowerReceivingPageResDTO;
import com.jude.server.Entity.Facility;
import com.jude.server.Entity.Room;
import com.jude.server.Repository.FacilityRepository;
import com.jude.server.Repository.RoomRepository;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final FacilityRepository facilityRepository;
    private final PowerReceivingService powerReceivingService;

    public RoomCreateResDTO roomCreateReq(
        RoomCreateReqDTO dto
    ){

        Facility facility = facilityRepository.findById(dto.getFacilityId())
            .orElseThrow(() -> new IllegalArgumentException("시설이 존재하지 않음"));
        Room entity = RoomCreateReqDTO.toEntity(dto, facility);

        Room saved = roomRepository.save(entity);

        return RoomCreateResDTO.toRes(saved);
    }

}
