package com.jude.server.Entity;

import com.jude.server.DTO.Enum.FacilityType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "facility")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter @Setter
public class Facility {
    @Id @Column(name = "facility_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facilityId;

    @Column(name = "mng_no", nullable = false, unique = true)
    private String mngNo;

    @Column
    private String facilityName;

    @Enumerated(EnumType.STRING)
    private FacilityType facilityType;

    @Column(name = "customer_no")
    private String customerNo;

    @OneToMany(mappedBy = "facility")
    private List<Room> rooms = new ArrayList<>();
}

