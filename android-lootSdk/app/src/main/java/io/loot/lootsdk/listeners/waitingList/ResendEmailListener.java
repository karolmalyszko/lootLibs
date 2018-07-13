package io.loot.lootsdk.listeners.waitingList;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface ResendEmailListener extends GenericFailListener {

    void onResendEmailSuccess();
    void onResendEmailError(String error);

}
