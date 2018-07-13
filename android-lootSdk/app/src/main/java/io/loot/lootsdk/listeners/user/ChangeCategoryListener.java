package io.loot.lootsdk.listeners.user;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface ChangeCategoryListener extends GenericFailListener {
    void onChangeCategorySuccess();
    void onChangeCategoryError(String error);
}
