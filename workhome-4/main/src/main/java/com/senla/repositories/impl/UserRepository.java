package com.senla.repositories.impl;

import com.senla.models.user.User;
import com.senla.repositories.AbstractRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserRepository extends AbstractRepository<User, Long> {
    @PostConstruct
    void init(){
        List<User> users = new ArrayList<>();
        initList(users);
        super.bulkInit(users);
    }

    private void initList(List<User> users) {
        User user1 = new User(1L, "Max", "wwww", "max@gmail.com",
                "hashedPassw1", "+375-11-111-22-33",
                LocalDateTime.of(2020,01,05, 4, 0), LocalDate.of(2010,02,06),
                1L);
        User user2 = new User(2L, "Vlad", "qqqq", "vlad@gmail.com",
                "hashedPassw2", "+375-99-111-22-33",
                LocalDateTime.of(2020,01,07, 0, 0), LocalDate.of(2010,07,01),
                1L);
        User driver1 = new User(3L, "Oleg", "aaaa", "oleg@gmail.com",
                "hashedPassw3", "+375-88-171-22-33",
                LocalDateTime.of(2019,01,07, 0 ,0), LocalDate.of(2009,07,01),
                2L);
        User driver2 = new User(4L, "Misha", "gggg", "misha@gmail.com",
                "hashedPassw4", "+375-34-164-22-33",
                LocalDateTime.of(2020,01,07, 0, 0), LocalDate.of(2010,07,01),
                2L);
        User admin1 = new User(5L, "Admin", "Admin", "admin@gmail.com",
                "hashedPassw5", "+375-00-111-22-33",
                LocalDateTime.of(2020,01,07, 0, 0), LocalDate.of(2010,07,01),
                3L);
        users.add(user1);
        users.add(user2);
        users.add(driver1);
        users.add(driver2);
        users.add(admin1);
    }

    @Override
    protected void postSaveProcessEntity(User entity) {
        entity.setRegistrationDate(LocalDateTime.now());
        hashPassword(entity);
    }
    @Override
    protected void postUpdateProcessEntity(User entity) {
        hashPassword(entity);
    }

    @Override
    protected Long idGenNext() {
        return super.currentId++;
    }
    private void hashPassword(User entity) {
        entity.setPassword(entity.getPassword() + "_HASHED_THROUGH_POSTSAVE_IMPL");
    }

}
