package io.loot.lootsdk.listeners.forgottenPassword;

import io.loot.lootsdk.listeners.GenericFailListener;

public interface ConfirmPasswordResetListener extends GenericFailListener {
    void onConfirmPasswordResetSuccess();
    void onConfirmPasswordResetError(String error);
}
