package com.jude.server.Repository;

import com.jude.server.Entity.Lighting;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LightingRepository extends JpaRepository<Lighting, Long> {
    @Query("""
        SELECT l
        FROM Lighting l
        JOIN FETCH l.facility f
        ORDER BY f.facilityId
    """) List<Lighting> findAllWithFacility();

    @Query("""
        SELECT l
        FROM Lighting l
        JOIN FETCH l.facility f
        WHERE f.facilityId = :facilityId
    """) Optional<Lighting> findByFacilityId(@Param("facilityId") Long facilityId);
}
