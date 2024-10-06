package com.senla.models.shift;

import com.senla.models.cab.Cab;
import com.senla.models.driver.Driver;
import com.senla.models.match.Match;
import com.senla.models.rate.Rate;
import com.senla.models.ride.Ride;
import com.senla.util.Identifiable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "shifts")
public class Shift implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shift_id")
    private Long shiftId;

    @Column(name = "starttime", nullable = false)
    @EqualsAndHashCode.Exclude
    private LocalDateTime starttime;

    @Column(name = "endtime")
    @EqualsAndHashCode.Exclude
    private LocalDateTime endtime;



    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cab_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Cab cab;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_rate_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Rate rate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Driver driver;

    @OneToMany(mappedBy = "shift")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Ride> rides = new HashSet<>();

//
//
    @OneToMany(mappedBy = "shift", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<Match> matches = new HashSet<>();



    @Override
    @Transient
    public Long getId() {
        return shiftId;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.shiftId = aLong;
    }
}
