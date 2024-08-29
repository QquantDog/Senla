package com.senla.repositories.impl;

import com.senla.models.card.Card;
import com.senla.models.cityrate.CityRate;
import com.senla.repositories.AbstractRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class CardRepository extends AbstractRepository<Card, Long> {
    @PostConstruct
    void init(){
        List<Card> cards = new ArrayList<>();
        initList(cards);
        super.bulkInit(cards);
    }

    private void initList(List<Card> cards) {
        Card card = new Card(1L, "1234-5678-8765-4321", LocalDate.of(2025,1,1), "NAME SURNAME", 1L);
        cards.add(card);
    }

//    private Long cardId;
//    private String cardNumber;
//    private LocalDate expirationDate;
//    private String cardHolderName;
//
//    private Long customerId;

    @Override
    protected void postSaveProcessEntity(Card entity) {}
    @Override
    protected void postUpdateProcessEntity(Card entity) {}

    @Override
    protected Long idGenNext() {
        return super.currentId++;
    }
}
