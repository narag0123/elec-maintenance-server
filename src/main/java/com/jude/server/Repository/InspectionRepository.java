package com.jude.server.Repository;

import com.jude.server.DTO.Enum.InspectionType;
import com.jude.server.Entity.Inspection.Inspection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InspectionRepository extends JpaRepository<Inspection, Long> {
    List<Inspection> findByFacilityFacilityIdIn(List<Long> facilityIds);

    @Query("""
        SELECT i
        FROM Inspection i
        JOIN FETCH i.facility f
        WHERE f.facilityId = :facilityId
        ORDER BY i.inspectionId ASC
    """)
    List<Inspection> findAllByFacilityIdForDetail(@Param("facilityId") Long facilityId);

    @Query("""
        SELECT i
        FROM Inspection i
        JOIN FETCH i.facility f
        WHERE f.facilityId = :facilityId
          AND i.inspectionType = :inspectionType
    """)
    Optional<Inspection> findForUpdate(
        @Param("facilityId") Long facilityId,
        @Param("inspectionType") InspectionType inspectionType

    );
}
