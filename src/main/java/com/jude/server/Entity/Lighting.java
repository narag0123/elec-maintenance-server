package com.jude.server.Entity;

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
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "lighting")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Lighting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lighting_id")
    private Long lightingId;

    /*
     * 터널등
     */

    @Column(name = "tunnel_integrated_led_count")
    private Long tunnelIntegratedLedCount;

    @Column(name = "tunnel_led_lamp_count")
    private Long tunnelLedLampCount;

    @Column(name = "tunnel_normal_count")
    private Long tunnelNormalCount;

    /*
     * 가로등
     */

    @Column(name = "street_led_count")
    private Long streetLedCount;

    @Column(name = "street_normal_count")
    private Long streetNormalCount;

    @Column(name = "lighting_memo")
    private String lightingMemo;

    /*
     * Facility 1 : 1
     */
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "facility_id",
        nullable = false,
        unique = true
    )
    private Facility facility;
}