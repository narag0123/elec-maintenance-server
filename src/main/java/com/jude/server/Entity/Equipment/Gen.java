package com.jude.server.Entity.Equipment;

import com.jude.server.DTO.Enum.GenType;
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
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "gen")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class Gen {

    @Id
    @Column(name = "gen_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long genId;

    @Column(name = "gen_capa_kva")
    private Long genCapaKva; // kva

    @Column(name = "gen_voltage_v")
    private Long genVoltageV;

    @Enumerated(EnumType.STRING)
    private GenType genType;

    @Column(name = "last_consumable_exchange_date")
    private LocalDate lastConsumableExchangeDate;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "emergency_equipment_id",
        nullable = false,
        unique = true
    )
    @ToString.Exclude
    private EmergencyEquipment emergencyEquipment;
}
