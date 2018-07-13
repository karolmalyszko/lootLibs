package io.loot.lootsdk.listeners.signup;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface StartKYCListener extends GenericFailListener {

    void onStartedSuccessfully();
    void onError(String error);

}
