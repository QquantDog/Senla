package com.senla.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.config.test.TestContainerConfig;
import com.senla.config.test.TestWebConfig;
import com.senla.dto.shift.ShiftFullResponseDto;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;


import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TestContainerConfig.class, TestWebConfig.class})
@WebAppConfiguration
public class ShiftTest {

//    @Autowired
//    private UserDao userDao;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;



    @Container
    public static PostgreSQLContainer<?> postgresDB = new PostgreSQLContainer<>(
            DockerImageName.parse("postgis/postgis:16-master")
                    .asCompatibleSubstituteFor("postgres")
    )
            .withDatabaseName("test_container_db")
            .withUsername("postgres")
            .withPassword("postgres")
            .withExposedPorts(5432);

    @BeforeAll
    public static void beforeAll() {
        postgresDB.start();
    }

    @BeforeEach
    public void beforeEach() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DynamicPropertySource
    public static void pgProperties(DynamicPropertyRegistry registry) {
        registry.add("testcontainer.url", postgresDB::getJdbcUrl);
        registry.add("testcontainer.user", postgresDB::getUsername);
        registry.add("testcontainer.pass", postgresDB::getPassword);
    }


    @Test
    public void testGetAllShifts() throws Exception {

        MvcResult result = mockMvc.perform(get("/shifts"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$[*].shift_id").hasJsonPath())
                .andExpect(jsonPath("$[*].start_time").hasJsonPath())
                .andExpect(jsonPath("$[*].end_time").hasJsonPath())
                .andExpect(jsonPath("$[*].rate_info").hasJsonPath())
                .andExpect(jsonPath("$[*].driver_info").hasJsonPath())
                .andExpect(jsonPath("$[*].cab_info").hasJsonPath())
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<ShiftFullResponseDto> arr = objectMapper.readValue(json, new TypeReference<>() {});
        Assertions.assertNotNull(arr);
        Assertions.assertEquals(8, arr.size());
    }

    @Test
    public void createShift1() throws Exception {

        String startShiftJson = """
                {
                	"city_id": 2,
                	"driver_id": 3,
                	"cab_id": 3,
                	"shift_start_point": {
                			"type": "Point",
                			"coordinates": [
                				41.999,
                				23.999
                			]
                		}
                }""";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/shifts/start")
                        .content(startShiftJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("shift_id").value(9))
                .andExpect(jsonPath("start_time").isNotEmpty())
                .andExpect(jsonPath("end_time").isEmpty())
                .andExpect(jsonPath("rate_info").hasJsonPath())
                .andExpect(jsonPath("rate_info.init_price").value(3.3))
                .andExpect(jsonPath("rate_info.rate_per_km").value(1.8))
                .andExpect(jsonPath("driver_info").hasJsonPath())
                .andExpect(jsonPath("driver_info.driver_id").value(3))
                .andExpect(jsonPath("cab_info").hasJsonPath())
                .andExpect(jsonPath("cab_info.cab_id").value(3))
                .andExpect(jsonPath("cab_info.vin").value("vin3"))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void errorStartingShiftWithActiveDriverAndCab() throws Exception {
        String startShift1Json = """
                {
                	"city_id": 2,
                	"driver_id": 5,
                	"cab_id": 2,
                	"shift_start_point": {
                			"type": "Point",
                			"coordinates": [
                				41.999,
                				23.999
                			]
                		}
                }""";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/shifts/start")
                        .content(startShift1Json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("status").value(500))
                .andExpect(jsonPath("timestamp").isNotEmpty())
                .andExpect(jsonPath("message").isNotEmpty())
                .andExpect(status().is5xxServerError());
    }

    @Test
    public void endShift() throws Exception {
        String endShiftJson = """
                {
                	"driver_id": 5,
                	"shift_id": 5
                }""";

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/shifts/finish")
                        .content(endShiftJson)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("shift_id").value(5))
                .andExpect(jsonPath("start_time").isNotEmpty())
                .andExpect(jsonPath("end_time").isNotEmpty())
                .andExpect(jsonPath("rate_info").hasJsonPath())
                .andExpect(jsonPath("rate_info.init_price").hasJsonPath())
                .andExpect(jsonPath("rate_info.rate_per_km").hasJsonPath())
                .andExpect(jsonPath("driver_info").hasJsonPath())
                .andExpect(jsonPath("driver_info.driver_id").hasJsonPath())
                .andExpect(jsonPath("cab_info").hasJsonPath())
                .andExpect(jsonPath("cab_info.cab_id").hasJsonPath())
                .andExpect(jsonPath("cab_info.vin").hasJsonPath())
                .andExpect(status().isCreated());
    }
}

//Ordered
//.andExpect(jsonPath("shift_id").value(9))
//        .andExpect(jsonPath("start_time").isNotEmpty())
//        .andExpect(jsonPath("end_time").isEmpty())
//        .andExpect(jsonPath("rate_info").hasJsonPath())
//        .andExpect(jsonPath("rate_info.init_price").value(3.3))
//        .andExpect(jsonPath("rate_info.rate_per_km").value(1.8))
//        .andExpect(jsonPath("driver_info").hasJsonPath())
//        .andExpect(jsonPath("driver_info.driver_id").value(3))
//        .andExpect(jsonPath("cab_info").hasJsonPath())
//        .andExpect(jsonPath("cab_info.cab_id").value(3))
//        .andExpect(jsonPath("cab_info.vin").value("vin3"))
//        .andExpect(status().isCreated());