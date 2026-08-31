package com.jude.server.Service;

import com.jude.server.DTO.Res.Detail.Lighting.LightingDetailResDTO;
import com.jude.server.DTO.Res.Detail.Lighting.LightingUpdateReqDTO;
import com.jude.server.DTO.Res.Pagenation.Lighting.LightingResDTO;
import com.jude.server.Entity.Facility;
import com.jude.server.Entity.Lighting;
import com.jude.server.Repository.FacilityRepository;
import com.jude.server.Repository.LightingRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LightingService {

    private final LightingRepository lightingRepository;
    private final FacilityRepository facilityRepository;

    @Transactional(readOnly = true)
    public List<LightingResDTO> getAll() {
        List<Lighting> lightings =
            lightingRepository.findAllWithFacility();

        return lightings.stream()
            .map(lighting ->
                LightingResDTO.toRes(
                    lighting.getFacility(),
                    lighting
                )
            )
            .toList();
    }

    @Transactional(readOnly = true)
    public LightingResDTO getByFacility(
        Long facilityId
    ) {

        Lighting lighting =
            lightingRepository.findByFacilityId(
                    facilityId
                )
                .orElseThrow(
                    () -> new RuntimeException(
                        "조명 정보가 존재하지 않습니다."
                    )
                );

        return LightingResDTO.toRes(
            lighting.getFacility(),
            lighting
        );
    }

    @Transactional(readOnly = true)
    public LightingDetailResDTO detail(
        Long facilityId
    ) {
        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new RuntimeException("시설 정보를 찾을 수 없습니다."));

        Lighting lighting = lightingRepository.findByFacilityId(facilityId)
                .orElse(null);

        return LightingDetailResDTO.toRes(
            facility,
            lighting
        );
    }

    @Transactional
    public LightingDetailResDTO update(
        Long facilityId,
        LightingUpdateReqDTO req
    ) {

        Lighting lighting =
            lightingRepository.findByFacilityId(
                    facilityId
                )
                .orElseThrow(
                    () -> new RuntimeException(
                        "조명 정보를 찾을 수 없습니다."
                    )
                );

        req.updateEntity(lighting);

        return LightingDetailResDTO.toRes(
            lighting.getFacility(),
            lighting
        );
    }
}
