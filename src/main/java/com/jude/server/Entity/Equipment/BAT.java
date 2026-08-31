package com.jude.server.Entity.Equipment;

import com.jude.server.DTO.Enum.EmergencyEquipmentType;
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
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "bat")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class BAT {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bat_id")
    private Long batId;

    @Column(name = "bat_capa_ah")
    private Long batCapaAh;

    @Column(name = "bat_voltage_v")
    private Long batVoltageV;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "bat_model")
    private String batModel;

    @Column(name = "total_capa_kwh")
    private Long totalCapaKwh;

    @Column(name = "install_date")
    private LocalDate installDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emergency_equipment_id")
    @ToString.Exclude
    private EmergencyEquipment emergencyEquipment;

}
