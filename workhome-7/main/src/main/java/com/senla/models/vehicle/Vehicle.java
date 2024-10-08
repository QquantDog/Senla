package com.senla.models.vehicle;

import com.senla.models.cab.Cab;
import com.senla.models.ratetier.RateTier;
import com.senla.models.vehiclebrand.VehicleBrand;
import com.senla.util.Identifiable;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "vehicles")
public class Vehicle implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_id")
    private Long vehicleId;

    @Column(name = "vehicle_model", nullable = false)
    @EqualsAndHashCode.Exclude
    private String vehicleModel;

    @Column(name="seats_number", nullable = false)
    @EqualsAndHashCode.Exclude
    private Integer seatsNumber;



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tier_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private RateTier rateTier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private VehicleBrand brand;

    @OneToMany(mappedBy = "vehicle")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Cab> cabs = new HashSet<>();



    @Override
    @Transient
    public Long getId() {
        return vehicleId;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.vehicleId = aLong;
    }
}
