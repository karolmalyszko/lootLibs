package io.loot.lootsdk.listeners.waitingList;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface RegisterToWaitingListListener extends GenericFailListener {

    void onRegisteredSuccessfully();
    void onAlreadyOnWaitingList();
    void onRegisteredAsRegularAccount();
    void onRegisterError(String error);

}
