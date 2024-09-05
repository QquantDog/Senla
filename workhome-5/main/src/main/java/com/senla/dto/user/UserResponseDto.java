package com.senla.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {

    private String firstName;
    private String lastName;
    private String email;

    private String phoneNumber;
    private LocalDateTime registrationDate;
    private LocalDate birthdate;

    //  N->1 (ManyToOne)
//    private RoleEntity role;
}
