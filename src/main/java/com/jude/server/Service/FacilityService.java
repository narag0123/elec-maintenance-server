package com.jude.server.Service;

import com.jude.server.DTO.Req.Create.FacilityCreateReqDTO;
import com.jude.server.DTO.Res.Create.FacilityCreateResDTO;
import com.jude.server.Entity.Facility;
import com.jude.server.Repository.FacilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacilityService {

    private final FacilityRepository facilityRepository;
    private final RoomService roomService;


    public FacilityCreateResDTO facilityCreateReq(
        FacilityCreateReqDTO dto
    ) {
        Facility facility = FacilityCreateReqDTO.toEntity(dto);
        Facility savedFacility = facilityRepository.save(facility);

        return FacilityCreateResDTO.toRes(savedFacility);
    }


}
