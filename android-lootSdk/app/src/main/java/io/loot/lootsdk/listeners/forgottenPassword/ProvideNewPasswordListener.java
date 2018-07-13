package io.loot.lootsdk.listeners.forgottenPassword;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.forgottenPassword.ProvidePasswordMethod;

public interface ProvideNewPasswordListener extends GenericFailListener{
    void onProvideNewPasswordSuccess(ProvidePasswordMethod providePasswordMethod);
    void onProvideNewPasswordError(String error);
}
