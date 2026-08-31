package com.jude.server.Repository;

import com.jude.server.Entity.Equipment.Gen;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GenRepository extends JpaRepository<Gen, Long> {

    @Query("""
      SELECT g
      FROM Gen g
      JOIN FETCH g.emergencyEquipment ee
      JOIN FETCH ee.room r
      WHERE r.roomId IN :roomIds
      ORDER BY g.genId ASC
    """)
    List<Gen> findByRoomIds(List<Long> roomIds);

    @Query(
        value = """
            SELECT g
            FROM Gen g
            JOIN FETCH g.emergencyEquipment ee
            JOIN FETCH ee.room r
            JOIN FETCH r.facility f
            order by f.facilityId
            """,
        countQuery = """
            SELECT COUNT(g)
            FROM Gen g
            """
    )
    Page<Gen> findAllGenForPage(Pageable pageable);

    @Query("""
        SELECT g
        FROM Gen g
        JOIN FETCH g.emergencyEquipment ee
        JOIN FETCH ee.room r
        JOIN FETCH r.facility f
        WHERE f.facilityId IN :facilityIds
        ORDER BY f.facilityId, g.genId ASC
    """)
    List<Gen> findByFacilityIds(
        @Param("facilityIds") List<Long> facilityIds
    );


    @Query("""
        SELECT g
        FROM Gen g
        JOIN FETCH g.emergencyEquipment ee
        JOIN FETCH ee.room r
        JOIN FETCH r.facility f
        WHERE f.facilityId = :facilityId
        ORDER BY g.genId ASC
    """)


    List<Gen> findAllByFacilityIdForDetail(
        @Param("facilityId") Long facilityId
    );
    @Query("""
    SELECT g
    FROM Gen g
    JOIN FETCH g.emergencyEquipment ee
    JOIN FETCH ee.room r
    JOIN FETCH r.facility f
    WHERE g.genId = :genId
      AND ee.emergencyEquipmentId = :emergencyEquipmentId
      AND f.facilityId = :facilityId
    """)
    Optional<Gen> findForUpdate(
        @Param("genId") Long genId,
        @Param("emergencyEquipmentId") Long emergencyEquipmentId,
        @Param("facilityId") Long facilityId
    );

    @Query("""
        SELECT g
        FROM Gen g
        JOIN FETCH g.emergencyEquipment ee
        JOIN FETCH ee.room r
        WHERE r.roomId = :roomId
        ORDER BY g.genId ASC
    """)
    List<Gen> findByRoomIdForInspectionDetail(
        @Param("roomId") Long roomId
    );


}
