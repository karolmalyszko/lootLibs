package io.loot.lootsdk.listeners.forgottenPassword;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.forgottenPassword.PhoneValidationNameAndCode;

public interface CheckPhoneValidationListener extends GenericFailListener {
    void onCheckPhoneValidationSuccess(PhoneValidationNameAndCode phoneValidationNameAndCode);
    void onCheckPhoneValidationError(String error);
}
