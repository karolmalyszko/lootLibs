package io.loot.lootsdk.listeners.forgottenPassword;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.forgottenPassword.ForgotPasswordMethod;

public interface ResetPasswordListener extends GenericFailListener {
    void onResetPasswordSuccess(ForgotPasswordMethod forgotPasswordMethod);
    void onResetPasswordError(String error);
}
