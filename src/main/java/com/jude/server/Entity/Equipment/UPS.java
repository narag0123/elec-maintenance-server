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
@Table(name = "ups")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class UPS {
    @Id
    @Column(name = "ups_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long upsId;

    @Column(name = "ups_voltage_v")
    private Long upsVoltageV;

    @Column(name = "ups_capa_kva")
    private Long upsCapaKva;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "emergency_equipment_id",
        nullable = false,
        unique = true
    )
    @ToString.Exclude
    private EmergencyEquipment emergencyEquipment;


}
