package com.jude.server.Repository;

import com.jude.server.DTO.Enum.RoomType;
import com.jude.server.Entity.Room;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findByFacilityFacilityIdIn(List<Long> facilityIds);

    @Query("""
        SELECT r
        FROM Room r
        WHERE r.facility.facilityId = :facilityId
        ORDER BY r.roomId ASC
    """) List<Room> findByFacilityId(@Param("facilityId") Long facilityId);

    @Query("""
        SELECT r
        FROM Room r
        JOIN FETCH r.facility f
        WHERE f.facilityId = :facilityId
          AND r.roomType = :roomType
        ORDER BY r.roomId ASC
    """)
    List<Room> findByFacilityIdAndRoomType(
        @Param("facilityId") Long facilityId,
        @Param("roomType") RoomType roomType
    );
}
