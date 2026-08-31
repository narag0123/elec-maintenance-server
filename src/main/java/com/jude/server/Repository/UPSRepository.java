package com.jude.server.Repository;

import com.jude.server.Entity.Equipment.UPS;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UPSRepository extends JpaRepository<UPS, Long> {
    @Query("""
      SELECT u
      FROM UPS u
      JOIN FETCH u.emergencyEquipment ee
      JOIN FETCH ee.room r
      WHERE r.roomId IN :roomIds
    """)
    List<UPS> findByRoomIds(List<Long> roomIds);

    @Query(
        value = """
            SELECT u
            FROM UPS u
            JOIN FETCH u.emergencyEquipment ee
            JOIN FETCH ee.room r
            JOIN FETCH r.facility f
            order by f.facilityId
            """,
        countQuery = """
            SELECT COUNT(u)
            FROM UPS u
            """
    )
    Page<UPS> findAllWithFacility(Pageable pageable);

    @Query("""
        select u
        from UPS u
        join fetch u.emergencyEquipment ee
        join fetch ee.room r
        join fetch r.facility f
        where f.facilityId in :facilityIds
        order by f.facilityId, u.upsId 
    """)
    List<UPS> findByFacilityIds(List<Long> facilityIds);



    @Query("""
        SELECT u
        FROM UPS u
        JOIN FETCH u.emergencyEquipment ee
        JOIN FETCH ee.room r
        JOIN FETCH r.facility f
        WHERE f.facilityId = :facilityId
        ORDER BY u.upsId ASC
    """)
    List<UPS> findAllByFacilityIdForDetail(
        @Param("facilityId") Long facilityId
    );

    @Query("""
        SELECT u
        FROM UPS u
        JOIN FETCH u.emergencyEquipment ee
        JOIN FETCH ee.room r
        JOIN FETCH r.facility f
        WHERE u.upsId = :upsId
          AND ee.emergencyEquipmentId = :emergencyEquipmentId
          AND f.facilityId = :facilityId
    """)
    Optional<UPS> findForUpdate(
        @Param("upsId") Long upsId,
        @Param("emergencyEquipmentId") Long emergencyEquipmentId,
        @Param("facilityId") Long facilityId
    );

    @Query("""
        SELECT u
        FROM UPS u
        JOIN FETCH u.emergencyEquipment ee
        JOIN FETCH ee.room r
        WHERE r.roomId = :roomId
        ORDER BY u.upsId ASC
    """)
    List<UPS> findByRoomIdForInspectionDetail(
        @Param("roomId") Long roomId
    );


}
