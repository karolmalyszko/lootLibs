package io.loot.lootsdk.listeners.signup;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.networking.signup.ConfirmDataResponse;

public interface ConfirmIdentityListener extends GenericFailListener {
    void onConfirmIdentitySuccess(ConfirmDataResponse response);
    void onConfirmIdentityError(String error);
}
