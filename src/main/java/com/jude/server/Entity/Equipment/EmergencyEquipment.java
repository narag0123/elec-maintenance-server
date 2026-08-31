package com.jude.server.Entity.Equipment;

import com.jude.server.DTO.Enum.EmergencyEquipmentType;
import com.jude.server.Entity.Room;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "emergency_equipment")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class EmergencyEquipment {
    @Id
    @Column(name = "emergency_equipment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emergencyEquipmentId;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "install_date")
    private LocalDate installDate;

    @Column(name = "cubicle_no")
    private String cubicleNo;

    @Column(name = "model")
    private String model;

    @Enumerated(EnumType.STRING)
    private EmergencyEquipmentType emergencyEquipmentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @ToString.Exclude
    private Room room;


}
