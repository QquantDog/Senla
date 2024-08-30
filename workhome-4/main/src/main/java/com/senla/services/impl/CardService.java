package com.senla.services.impl;

import com.senla.models.card.Card;
import com.senla.repositories.impl.CardRepository;
import com.senla.util.service.AbstractLongIdGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CardService extends AbstractLongIdGenericService<Card> {
    @Autowired
    public CardService(CardRepository cardRepository) {
        this.abstractRepository = cardRepository;
    }
}
