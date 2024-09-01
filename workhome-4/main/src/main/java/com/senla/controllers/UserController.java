package com.senla.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.senla.dto.user.UserCreateDto;
import com.senla.models.user.User;
import com.senla.dto.user.UserResponseDto;
import com.senla.dto.user.UserUpdateDto;
import com.senla.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UserController {

    private final UserService userService;
    private final ObjectMapper jsonMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ObjectMapper jsonMapper, ModelMapper modelMapper) {
        this.userService = userService;
        this.jsonMapper = jsonMapper;
        this.modelMapper = modelMapper;
    }

    public String getAllUsers() throws JsonProcessingException {
        Collection<User> users = this.userService.findAll();
        return buildUserResponse(
                users.stream()
                        .map(this::convertToResponseDto)
                        .collect(Collectors.toList()));
    }

    public String getUserById(Long id) throws JsonProcessingException {
        Optional<User> user = this.userService.findById(id);
        if(user.isPresent()){
            return buildUserResponse(convertToResponseDto(user.get()));
        } else{
            return "No user found";
        }
    }
    public String createUser(UserCreateDto userCreateDto) throws JsonProcessingException {
        User user = this.modelMapper.map(userCreateDto, User.class);
        return buildUserResponse(convertToResponseDto(userService.save(user)));
    }
    public String updateUser(UserUpdateDto userUpdateDto) throws JsonProcessingException {
        User user = this.modelMapper.map(userUpdateDto, User.class);
        return buildUserResponse(convertToResponseDto(userService.save(user)));
    }

    public void deleteUser(Long id) {
        userService.deleteById(id);
    }

    public void existsUser(Long id) {
        System.out.println("Does exist user with ID: " + id +  " " +userService.existsById(id));
    }

    private UserResponseDto convertToResponseDto(User user){
        return modelMapper.map(user, UserResponseDto.class);
    }

    private String buildUserResponse(List<UserResponseDto> usersResponseList) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(usersResponseList);
    }
    private String buildUserResponse(UserResponseDto usersResponseDto) throws JsonProcessingException {
        return jsonMapper.writeValueAsString(usersResponseDto);
    }

}
