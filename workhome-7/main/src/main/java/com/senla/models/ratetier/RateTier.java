package com.senla.models.ratetier;

import com.senla.models.rate.Rate;
import com.senla.models.vehicle.Vehicle;
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
@Table(name = "rate_tiers")
public class RateTier implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tier_id")
    private Long tierId;

    @Column(name = "tier_name", nullable = false)
    @EqualsAndHashCode.Exclude
    private String tierName;

    @Column
    @EqualsAndHashCode.Exclude
    private String description;

//    2 связи сюда OneToMany mapped_by rateTier(rates и vehicles)
//    Transient - нужен на поле?


    @OneToMany(mappedBy = "rateTier")
    @EqualsAndHashCode.Exclude
    private Set<Rate> rates = new HashSet<>();

    @OneToMany(mappedBy = "rateTier")
    @EqualsAndHashCode.Exclude
    private Set<Vehicle> vehicles = new HashSet<>();




    @Override
    @Transient
    public Long getId() {
        return tierId;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.tierId = aLong;
    }
}
