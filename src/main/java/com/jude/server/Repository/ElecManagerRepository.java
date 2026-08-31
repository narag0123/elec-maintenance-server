package com.jude.server.Repository;

import com.jude.server.Entity.Inspection.ElecManager;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ElecManagerRepository extends JpaRepository<ElecManager, Long> {
    @Query("""
        SELECT em
        FROM ElecManager em
        JOIN FETCH em.facility f
        ORDER BY f.facilityId, em.elecManagerType
    """)
    List<ElecManager> findAllWithFacility();

}
