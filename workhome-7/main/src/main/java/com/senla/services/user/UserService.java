package com.senla.services.user;

import com.senla.dto.user.UserCreateDto;
import com.senla.dto.user.UserUpdateDto;
import com.senla.models.user.User;
import com.senla.util.service.GenericService;

import java.time.LocalDateTime;

public interface UserService extends GenericService<User, Long> {
    void findByEmail(String email);
    void allJoinWithPassengers();
    void findAllActiveDriversWithAccountRegisteredForMoreThanParameter(Integer months);
    void findUserBySpecification(String email, String firstName, String lastName, LocalDateTime from, LocalDateTime to);

    User createUser(UserCreateDto user);
    User updateUser(Long id, UserUpdateDto user);
}
