package com.jude.server.Repository;

import com.jude.server.Entity.Facility;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    @Query("""
        select distinct f
        from Facility f
        join Room r on r.facility = f
        join EmergencyEquipment ee on ee.room = r
        join UPS u on u.emergencyEquipment = ee
        order by f.facilityId asc
    """)
    Page<Facility> findAllHasUPS(Pageable pageable);

    @Query(
        value = """
        SELECT DISTINCT f
        FROM Facility f
        JOIN Room r ON r.facility = f
        JOIN EmergencyEquipment ee ON ee.room = r
        JOIN Gen g ON g.emergencyEquipment = ee
        ORDER BY f.facilityId ASC
        """,
        countQuery = """
        SELECT COUNT(DISTINCT f)
        FROM Facility f
        JOIN Room r ON r.facility = f
        JOIN EmergencyEquipment ee ON ee.room = r
        JOIN Gen g ON g.emergencyEquipment = ee
        """
    )
    Page<Facility> findAllHasGen(Pageable pageable);
}
