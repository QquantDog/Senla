package com.senla.models.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardCreateDto {
    private String cardNumber;
    private LocalDate expirationDate;
    private String cardHolderName;

    private Long customerId;
}