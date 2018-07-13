package io.loot.lootsdk.models.data.cards;

import java.io.Serializable;

public class CurrentCardHolder implements Serializable {

    private Card orderedCard;
    private Card currentCard;

    public CurrentCardHolder(Card orderedCard, Card currentCard) {
        this.orderedCard = orderedCard;
        this.currentCard = currentCard;
    }

    public Card getOrderedCard() {
        if (orderedCard == null) {
            orderedCard = new Card();
        }

        return orderedCard;
    }

    public void setOrderedCard(Card orderedCard) {
        this.orderedCard = orderedCard;
    }

    public Card getCurrentCard() {
        if (currentCard == null) {
            currentCard = new Card();
        }

        return currentCard;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }
}
