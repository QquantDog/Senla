package com.senla.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserCreateDto {

    @JsonProperty("name")
    private String firstName;

    @JsonProperty("surname")
    private String lastName;

    private String email;

    private String password;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("role_id")
    private Long roleId;
}
