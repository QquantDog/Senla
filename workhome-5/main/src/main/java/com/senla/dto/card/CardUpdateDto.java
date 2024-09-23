package com.senla.dto.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardUpdateDto {
    private Long cardId;
    private String cardNumber;
    private LocalDate expirationDate;
    private String cardHolderName;

    private Long customerId;
}
