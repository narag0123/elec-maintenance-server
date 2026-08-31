package com.jude.server.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "power_recieving")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class PowerReceiving {
    @Id @Column(name = "power_recieving_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long powerReceivingId;

    @Column(name = "capa_kw")
    private Long capaKw; // kw

    @Column(name = "voltage_v")
    private Long voltageV; // v

    @Column(name = "contract_kw")
    private Long contractKw; // kw

    @Column(name = "input_from")
    private String inputFrom; // ex) 한전

    @Column(name = "input_cb")
    private String inputCB; // ex) LBS

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", unique = true)
    private Room room;

}
