package io.loot.lootsdk.listeners.transfer;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface MakeTransferListener extends GenericFailListener {

    void onTransferMadeSuccessful();
    void onTransferError(String error);

}
