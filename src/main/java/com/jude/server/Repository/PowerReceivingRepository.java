package com.jude.server.Repository;

import com.jude.server.Entity.PowerReceiving;
import com.jude.server.Entity.Room;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PowerReceivingRepository extends JpaRepository<PowerReceiving, Long> {
    List<PowerReceiving> findByRoomRoomIdIn(List<Long> roomIds);
    List<Long> room(Room room);

    @Query("""
        SELECT pr
        FROM PowerReceiving pr
        JOIN FETCH pr.room r
        WHERE r.roomId IN :roomIds
    """) List<PowerReceiving> findByRoomIds(@Param("roomIds") List<Long> roomIds);

    @Query("""
        SELECT pr
        FROM PowerReceiving pr
        JOIN FETCH pr.room r
        JOIN FETCH r.facility f
        WHERE pr.powerReceivingId = :powerReceivingId
          AND r.roomId = :roomId
          AND f.facilityId = :facilityId
    """) Optional<PowerReceiving> findForUpdate(
        @Param("powerReceivingId") Long powerReceivingId,
        @Param("roomId") Long roomId,
        @Param("facilityId") Long facilityId
    );

    @Query("""
        SELECT p
        FROM PowerReceiving p
        JOIN FETCH p.room r
        WHERE r.roomId = :roomId
    """)
    Optional<PowerReceiving> findByRoomId(@Param("roomId") Long roomId);
}
