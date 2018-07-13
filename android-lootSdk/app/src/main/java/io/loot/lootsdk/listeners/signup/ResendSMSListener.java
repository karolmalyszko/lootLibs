package io.loot.lootsdk.listeners.signup;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface ResendSMSListener extends GenericFailListener {

    void onSMSSent();
    void onError(String error);

}
