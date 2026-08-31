package com.jude.server.Repository;

import com.jude.server.Entity.Equipment.Rectifier;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RectifierRepository
    extends JpaRepository<Rectifier, Long> {

    @Query("""
        SELECT r
        FROM Rectifier r
        JOIN FETCH r.emergencyEquipment ee
        JOIN FETCH ee.room room
        JOIN FETCH room.facility f
        ORDER BY f.facilityId, r.recId
    """)
    List<Rectifier> findAllWithFacility();

    @Query("""
        SELECT r
        FROM Rectifier r
        JOIN FETCH r.emergencyEquipment ee
        JOIN FETCH ee.room room
        JOIN FETCH room.facility f
        WHERE f.facilityId = :facilityId
        ORDER BY r.recId ASC
    """)
    List<Rectifier> findAllByFacilityIdForDetail(@Param("facilityId") Long facilityId);

    @Query("""
        SELECT r
        FROM Rectifier r
        JOIN FETCH r.emergencyEquipment ee
        JOIN FETCH ee.room room
        JOIN FETCH room.facility f
        WHERE r.recId = :recId
          AND ee.emergencyEquipmentId = :emergencyEquipmentId
          AND f.facilityId = :facilityId
    """)
    Optional<Rectifier> findForUpdate(
        @Param("recId") Long recId,
        @Param("emergencyEquipmentId") Long emergencyEquipmentId,
        @Param("facilityId") Long facilityId
    );
}