package io.loot.lootsdk.listeners.signup;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface PhoneVerificationListener extends GenericFailListener {

    void onSuccessfullyVerified();
    void onError(String error);

}
