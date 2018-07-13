package io.loot.lootsdk.listeners.signup;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface UpdatePersonalDataListener extends GenericFailListener {

    void onPersonalDataUpdated();
    void onError(String error);

}
