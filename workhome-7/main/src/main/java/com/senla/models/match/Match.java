package com.senla.models.match;

import com.senla.models.driver.Driver;
import com.senla.models.privilege.Privilege;
import com.senla.models.ride.Ride;
import com.senla.models.shift.Shift;
import com.senla.util.Identifiable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "matches")
public class Match implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long matchId;


    @Column(name = "upper_threshold", nullable = false)
    @EqualsAndHashCode.Exclude
    private LocalDateTime upperThreshold;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ride_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Ride ride;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "shift_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Shift shift;



    @Override
    @Transient
    public Long getId() {
        return matchId;
    }

    @Override
    @Transient
    public void setId(Long aLong) {
        this.matchId = aLong;
    }
}