package com.senla.models.card;

import com.senla.util.repository.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Card implements Identifiable<Long> {
    private Long cardId;
    private String cardNumber;
    private LocalDate expirationDate;
    private String cardHolderName;

    private Long customerId;

    @Override
    public Long getId() {
        return cardId;
    }

    @Override
    public void setId(Long aLong) {
        this.cardId = aLong;
    }
}
