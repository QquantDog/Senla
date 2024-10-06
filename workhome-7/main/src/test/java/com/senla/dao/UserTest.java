package com.senla.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.config.test.TestContainerConfig;
import com.senla.config.test.TestWebConfig;
import com.senla.dao.user.UserDao;
import com.senla.dto.user.UserCreateDto;
import com.senla.dto.user.UserResponseDto;
import com.senla.dto.user.UserUpdateDto;
import com.senla.models.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = {TestContainerConfig.class, TestWebConfig.class})
@WebAppConfiguration
public class UserTest {

    @Autowired
    private UserDao userDao;

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
//            .withReuse(true);

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
    public void testGetAllUsersController() throws Exception {
        MvcResult result = mockMvc.perform(get("/users"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$[*].name").hasJsonPath())
                .andExpect(jsonPath("$[*].email").hasJsonPath())
                .andExpect(jsonPath("$[*].surname").hasJsonPath())
                .andExpect(jsonPath("$[*].phone_number").hasJsonPath())
                .andExpect(jsonPath("$[*].registration_date").hasJsonPath())
                .andExpect(status().isOk())
                .andReturn();
        String json = result.getResponse().getContentAsString();
        List<UserResponseDto> usersResponseArr = objectMapper.readValue(json, new TypeReference<>(){});
        Assertions.assertNotNull(usersResponseArr);
        Assertions.assertEquals(8, usersResponseArr.size());

    }

    @Test
    void testCreateCustomer() throws Exception {
//        вынести в отдельный файл вынести
        String json = """
                        {
                            "name": "qwerty",
                            "surname": "gen_lastname1",
                            "email": "g1en_ema2il5",
                            "password": "g1en_pwd2",
                            "phone_number": "+1a21232",
                            "role_id": 1
                        }""";
        UserCreateDto userCreateDto = objectMapper.readValue(json, UserCreateDto.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("name").value(userCreateDto.getFirstName()))
                .andExpect(jsonPath("email").value(userCreateDto.getEmail()))
                .andExpect(jsonPath("surname").value(userCreateDto.getLastName()))
                .andExpect(jsonPath("phone_number").value(userCreateDto.getPhoneNumber()))
                .andExpect(jsonPath("registration_date").hasJsonPath())
                .andExpect(jsonPath("password").doesNotHaveJsonPath())
                .andExpect(status().isCreated());
    }

    @Test
    void testUpdateUser() throws Exception {
//        вынести в отдельный файл
        String json = """
                        {
                            "name": "testUpdateUser",
                            "surname": "testUpdateUser",
                            "email": "testUpdateUser@gmail.com",
                            "password": "testUpdateUserPWD",
                            "phone_number": "+testUpdateUserPhoneNumber"
                        }""";
        UserUpdateDto userUpdateDto = objectMapper.readValue(json, UserUpdateDto.class);

        Long idToUpdate = 1L;
        Assertions.assertTrue(userDao.findById(idToUpdate).isPresent());

//        objectMapper.writeValueAsString(new UserCreateDto());
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/users/1")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("name").value(userUpdateDto.getFirstName()))
                .andExpect(jsonPath("email").value(userUpdateDto.getEmail()))
                .andExpect(jsonPath("surname").value(userUpdateDto.getLastName()))
                .andExpect(jsonPath("phone_number").value(userUpdateDto.getPhoneNumber()))
                .andExpect(jsonPath("registration_date").hasJsonPath())
                .andExpect(jsonPath("password").doesNotHaveJsonPath())
                .andExpect(status().isCreated())
                .andReturn();
        Optional<User> userOptional = userDao.findById(idToUpdate);
        Assertions.assertTrue(userDao.findById(idToUpdate).isPresent());
        Assertions.assertEquals(idToUpdate, userOptional.get().getUserId());
        Assertions.assertEquals(userUpdateDto.getFirstName(), userOptional.get().getFirstName());
    }

}
