package com.senla.dao;

import com.senla.config.test.TestContainerConfig;
import com.senla.dao.ride.RideDao;
import com.senla.models.ride.Ride;
import com.senla.specification.RideSpecification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = TestContainerConfig.class)
//@Testcontainers
public class RideDaoTest {

    @Autowired
    private RideDao rideDao;

//    @Container
    static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("test_container_db")
            .withUsername("postgres")
            .withPassword("postgres")
            .withExposedPorts(5432)
            .withReuse(true);

    @BeforeAll
    static void beforeAll() {
        postgresDB.start();
    }

    @DynamicPropertySource
    static void pgProperties(DynamicPropertyRegistry registry) {
        registry.add("testcontainer.url", postgresDB::getJdbcUrl);
        registry.add("testcontainer.user", postgresDB::getUsername);
        registry.add("testcontainer.pass", postgresDB::getPassword);
    }

    @Test
    void testRideBySpecification() {
        Specification<Ride> spec = RideSpecification.buildSpecification(BigDecimal.ONE, BigDecimal.valueOf(10L), null, BigDecimal.valueOf(20.0), BigDecimal.valueOf(40.0), BigDecimal.valueOf(20.0), BigDecimal.valueOf(40.0));
        List<Ride> rides = rideDao.findBySpecification(spec);
        Assertions.assertFalse(rides.isEmpty());
        for(Ride ride : rides) {
//            Assertions.assertNull(ride.getPromocodeEnterCode());
            Assertions.assertTrue((ride.getStartPointLat()).compareTo(BigDecimal.valueOf(20L)) >= 0);
            Assertions.assertTrue((ride.getStartPointLat()).compareTo(BigDecimal.valueOf(40L)) <= 0);
        }
    }
}