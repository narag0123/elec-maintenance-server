package com.jude.server.Entity.Equipment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "emergency_load")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class EmergencyLoad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emergency_load_id")
    private Long emergencyLoadId;

    @Column(name = "emergency_load_name")
    private String emergencyLoadName;

    @Column(name = "emergency_load_cubicle_no")
    private String emergencyLoadCubicleNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emergency_equipment_id")
    @ToString.Exclude
    private EmergencyEquipment emergencyEquipment;

}
