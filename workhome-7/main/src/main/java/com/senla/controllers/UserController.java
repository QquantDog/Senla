package com.senla.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.dto.user.UserCreateDto;
import com.senla.dto.user.UserResponseWithRoleDto;
import com.senla.models.user.User;
import com.senla.dto.user.UserResponseDto;
import com.senla.dto.user.UserUpdateDto;
import com.senla.services.user.UserService;
import com.senla.util.NotFoundByIdException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() throws JsonProcessingException {
        List<User> users = userService.findAll();
        return new ResponseEntity<>(users.stream().map(this::convertToResponseDto).collect(Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long id) throws JsonProcessingException, NotFoundByIdException {
        Optional<User> user = this.userService.findById(id);
        if(user.isPresent()){
            return new ResponseEntity<>(convertToResponseDto(user.get()), HttpStatus.OK);
        } else{
            throw new NotFoundByIdException(User.class);
        }
    }

    @PostMapping
    public ResponseEntity<UserResponseWithRoleDto> createUser(@Valid @RequestBody UserCreateDto userCreateDto) throws JsonProcessingException {
        User user = userService.createUser(userCreateDto);
        return new ResponseEntity<>(convertToResponseWithRoleDto(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UserUpdateDto userUpdateDto, @PathVariable("id") Long id) throws JsonProcessingException {
        return new ResponseEntity<>(convertToResponseDto(userService.updateUser(id, userUpdateDto)), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private UserResponseDto convertToResponseDto(User user){
        return modelMapper.map(user, UserResponseDto.class);
    }
    private UserResponseWithRoleDto convertToResponseWithRoleDto(User user){
        return modelMapper.map(user, UserResponseWithRoleDto.class);
    }

}
