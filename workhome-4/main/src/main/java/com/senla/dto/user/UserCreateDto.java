package com.senla.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCreateDto {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;

    private LocalDate birthdate;
    private Long roleId;
}
