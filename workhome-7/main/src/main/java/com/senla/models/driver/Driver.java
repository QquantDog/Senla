package com.senla.models.driver;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.senla.models.driverregistry.DriverRegistry;
import com.senla.models.user.User;
import com.senla.models.taxicompany.TaxiCompany;
import com.senla.util.Identifiable;
import jakarta.persistence.*;
import lombok.*;
import org.locationtech.jts.geom.Point;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "drivers")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "driver_id")
public class Driver implements Identifiable<Long> {
    @Id
    @Column(name = "driver_id")
    private Long driverId;

    @Column(name = "is_on_shift", nullable = false)
    @EqualsAndHashCode.Exclude
    private Boolean isOnShift;

    @Column(name = "is_on_ride", nullable = false)
    @EqualsAndHashCode.Exclude
    private Boolean isOnRide;

    @Column(name = "current_lat", columnDefinition = "latitude")
    @EqualsAndHashCode.Exclude
    private BigDecimal currentLat;

    @Column(name = "current_long", columnDefinition = "longitude")
    @EqualsAndHashCode.Exclude
    private BigDecimal currentLong;

    @Column(name = "current_point")
    @EqualsAndHashCode.Exclude
    private Point currentPoint;



    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "driver_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<DriverRegistry> registrationEntries = new HashSet<>();


//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "driver_registry",
//            joinColumns = { @JoinColumn(name = "driver_id")},
//            inverseJoinColumns = { @JoinColumn(name = "company_id")}
//    )
//    @EqualsAndHashCode.Exclude
//    private Set<TaxiCompany> companies = new HashSet<>();


    @Transient
    public void finishShiftAndRestore(){
        if(isOnRide) throw new IllegalStateException("Driver is on-ride Couldn't restore driver");
        isOnShift = false;
        isOnRide = false;
        currentLat = null;
        currentLong = null;
        currentPoint = null;
    }

    @Override
    @Transient
    public Long getId() {
        return driverId;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.driverId = aLong;
    }
}
