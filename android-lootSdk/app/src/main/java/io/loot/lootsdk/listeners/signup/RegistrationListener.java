package io.loot.lootsdk.listeners.signup;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface RegistrationListener extends GenericFailListener {

    void onRegistrationSuccess();
    void onRegistrationError(String error);

}
