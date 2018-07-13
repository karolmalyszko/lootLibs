package io.loot.lootsdk.models.networking.cards;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.loot.lootsdk.models.data.cards.Card;
import io.loot.lootsdk.models.orm.CardEntity;
import lombok.Data;

@Data
public class CardsListResponse {

    @SerializedName("cards")
    ArrayList<CardResponse> cards;

    public static ArrayList<CardEntity> parseToEntityObject(CardsListResponse cardsListResponse) {
        if (cardsListResponse == null) {
            cardsListResponse = new CardsListResponse();
        }
        if (cardsListResponse.getCards() == null) {
            cardsListResponse.setCards(new ArrayList<CardResponse>());
        }
        ArrayList<CardEntity> cardEntities = new ArrayList<CardEntity>();
        for (CardResponse cardResponse : cardsListResponse.getCards()) {
            cardEntities.add(CardResponse.parseToEntityObject(cardResponse));
        }
        return cardEntities;
    }

    public static ArrayList<Card> parseToDataObject(CardsListResponse cardsListResponse) {
        if (cardsListResponse == null) {
            cardsListResponse = new CardsListResponse();
        }
        if (cardsListResponse.getCards() == null) {
            cardsListResponse.setCards(new ArrayList<CardResponse>());
        }
        ArrayList<Card> cardObjects = new ArrayList<Card>();
        for (CardResponse cardResponse : cardsListResponse.getCards()) {
            cardObjects.add(CardResponse.parseToDataObject(cardResponse));
        }
        return cardObjects;
    }
}
