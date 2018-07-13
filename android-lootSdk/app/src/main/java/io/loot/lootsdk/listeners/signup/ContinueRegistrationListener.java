package io.loot.lootsdk.listeners.signup;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.userinfo.OnBoardingUserData;

public interface ContinueRegistrationListener extends GenericFailListener {

    void onSuccessfullyContinued(OnBoardingUserData onBoardingUserData);
    void onError(String error);

}
