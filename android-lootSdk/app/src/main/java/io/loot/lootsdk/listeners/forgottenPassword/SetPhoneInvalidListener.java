package io.loot.lootsdk.listeners.forgottenPassword;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.forgottenPassword.PhoneValidationNameAndCode;

public interface SetPhoneInvalidListener extends GenericFailListener {
    void onSetPhoneInvalidSuccess(PhoneValidationNameAndCode phoneValidationNameAndCode);
    void onSetPhoneInvalidError(String error);
}
