package io.loot.lootsdk.listeners.signup;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface ChangePhoneNumberListener extends GenericFailListener {

    void onNumberChanged();
    void onError(String error);

}
