package io.loot.lootsdk.listeners.user;

import java.util.List;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.cards.Card;

public interface GetCardsListener extends GenericFailListener {

    void onGetCachedCards(List<Card> cards);
    void onGetCardsSuccess(List<Card> cards);
    void onGetCardsError(String error);

}
