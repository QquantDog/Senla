package com.senla;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.senla.config.SpringConfig;
import com.senla.controllers.UserController;
import com.senla.models.user.UserCreateDto;
import com.senla.models.user.UserUpdateDto;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserRunner {

    public static void main(String[] args) throws JsonProcessingException {
        var ctx = new AnnotationConfigApplicationContext(SpringConfig.class);
        UserController c = ctx.getBean(UserController.class);
//        testRead(c);
        testInsert(c);
        testUpdate(c);
        testDeleteAndRead(c);
    }
    private static void testRead(UserController c) throws JsonProcessingException {
        System.out.println(c.getUserById(3L));
        System.out.println(c.getAllUsers());
    }

    private static void testInsert(UserController c) throws JsonProcessingException {

        UserCreateDto userToInsert = UserCreateDto.builder().firstName("Alex")
                .lastName("Smith")
                .email("alex@gmail.com")
                .password("newPassword1")
                .phoneNumber("+345-23-352-23-12")
                .birthdate(LocalDate.of(2001,3,4))
                .roleId(1L)
                    .build();
        System.out.println("User To Insert: " + userToInsert.toString());
        System.out.println(c.createUser(userToInsert));

        System.out.println(c.getAllUsers());
    }
    private static void testUpdate(UserController c) throws JsonProcessingException {
        UserUpdateDto userToUpdate = UserUpdateDto.builder().userId(1L)
                .firstName("Grisha")
                .lastName("Kachinsky")
                .email("alex@gmail.com")
                .password("kewk111")
                .phoneNumber("+345-23-352-23-12")
                .birthdate(LocalDate.of(2001,3,4))
                .registrationDate(LocalDateTime.of(2010, 3,3 ,4, 5))
                .roleId(1L)
                    .build();
        System.out.println("User To Update: " + userToUpdate.toString());
        System.out.println(c.updateUser(userToUpdate));

        System.out.println(c.debugGetAllUsers());
    }
    private static void testDeleteAndRead(UserController c) throws JsonProcessingException {
        c.existsUser(1L);
        c.deleteUser(1L);
        c.existsUser(1L);
        System.out.println("Users after delete: " + c.debugGetAllUsers());
    }
}