package io.loot.lootsdk.listeners.transfer;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface ValidateTransferListener extends GenericFailListener {

    void onAccountNumberVerified(boolean isCorrect);

}
