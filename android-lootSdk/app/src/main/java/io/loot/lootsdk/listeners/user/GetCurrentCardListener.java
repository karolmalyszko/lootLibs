package io.loot.lootsdk.listeners.user;

import java.util.List;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.cards.Card;

public interface GetCurrentCardListener extends GenericFailListener {

    void onGetCachedCurrentCard(Card card);
    void onGetCurrentCardSuccess(Card card);
    void onGetCurrentCardError(String error);
    void onGetOrderedCurrentCard(Card card);

}
