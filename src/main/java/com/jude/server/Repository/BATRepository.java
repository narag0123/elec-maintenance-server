package com.jude.server.Repository;

import com.jude.server.Entity.Equipment.BAT;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BATRepository extends JpaRepository<BAT, Long> {
    @Query("""
        SELECT bat
        FROM BAT bat
        JOIN FETCH bat.emergencyEquipment ee
        JOIN FETCH ee.room room
        WHERE room.roomId IN :roomIds
    """)
    List<BAT> findByRoomIds(@Param("roomIds") List<Long> roomIds);

    @Query("""
        SELECT bat
        FROM BAT bat
        JOIN FETCH bat.emergencyEquipment ee
        WHERE ee.emergencyEquipmentId IN :equipmentIds
        ORDER BY bat.batId ASC
        """)
    List<BAT> findByEmergencyEquipmentIds(
        @Param("equipmentIds") List<Long> equipmentIds
    );

    @Query("""
        SELECT b
        FROM BAT b
        JOIN FETCH b.emergencyEquipment ee
        WHERE b.batId = :batId
          AND ee.emergencyEquipmentId = :emergencyEquipmentId
    """)
    Optional<BAT> findForGeneratorUpdate(
        @Param("batId") Long batId,
        @Param("emergencyEquipmentId") Long emergencyEquipmentId
    );

    @Query("""
        SELECT b
        FROM BAT b
        JOIN FETCH b.emergencyEquipment ee
        WHERE b.batId = :batId
          AND ee.emergencyEquipmentId = :emergencyEquipmentId
    """)
    Optional<BAT> findForUPSUpdate(
        @Param("batId") Long batId,
        @Param("emergencyEquipmentId") Long emergencyEquipmentId
    );

    @Query("""
        SELECT b
        FROM BAT b
        JOIN FETCH b.emergencyEquipment ee
        WHERE b.batId = :batId
          AND ee.emergencyEquipmentId = :emergencyEquipmentId
    """)
    Optional<BAT> findForRectifierUpdate(
        @Param("batId") Long batId,
        @Param("emergencyEquipmentId") Long emergencyEquipmentId
    );



}
