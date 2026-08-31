package com.jude.server.Repository;

import com.jude.server.Entity.Equipment.EmergencyEquipment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyEquipmentRepository extends JpaRepository<EmergencyEquipment, Long> {


}
