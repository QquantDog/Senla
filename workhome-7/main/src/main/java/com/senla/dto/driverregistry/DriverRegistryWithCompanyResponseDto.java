package com.senla.dto.driverregistry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.senla.dto.driver.DriverResponseDto;
import com.senla.dto.taxicompany.TaxiCompanyResponseDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DriverRegistryWithCompanyResponseDto {
    @NotNull
    @JsonProperty("registration_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate registrationDate;

    @NotNull
    @JsonProperty("registration_expiration_date")
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate registrationExpirationDate;

    @NotNull
    @JsonProperty("company_info")
    private TaxiCompanyResponseDto taxiCompany;
}
