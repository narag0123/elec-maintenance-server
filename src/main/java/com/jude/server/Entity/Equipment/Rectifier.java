package com.jude.server.Entity.Equipment;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "rec")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Rectifier {
    @Id
    @Column(name = "rec_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recId;

    @Column(name = "rec_voltage_v")
    private Long recVoltageV;

    @Column(name = "rec_capa_a")
    private Long recCapaA;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "emergency_equipment_id",
        nullable = false,
        unique = true
    )
    @ToString.Exclude
    private EmergencyEquipment emergencyEquipment;

}
