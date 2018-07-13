package io.loot.lootsdk;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import io.loot.lootsdk.exceptions.NullPointerListenerException;
import io.loot.lootsdk.listeners.pushNotifications.RegisterNotificationListener;
import io.loot.lootsdk.models.ActionResult;
import io.loot.lootsdk.models.networking.pushNotifications.LoginPushNotificationRequest;
import io.loot.lootsdk.models.networking.pushNotifications.SignupPushNotificationRequest;
import io.loot.lootsdk.models.networking.signup.OnBoardingUserDataResponse;
import io.loot.lootsdk.networking.ApiResponse;
import io.loot.lootsdk.networking.LootApiInterface;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import io.loot.lootsdk.utils.AppExecutors;
import okhttp3.ResponseBody;

public class PushNotification {
    private LootSDK mLootSDK;

    PushNotification(LootSDK lootSDK) {
        mLootSDK = lootSDK;
    }

    public LiveData<ActionResult> registerFromSignUp(String cloudMessagingToken, String deviceId) {
        final SignupPushNotificationRequest request = new SignupPushNotificationRequest(cloudMessagingToken, deviceId);
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkBoundAction<OnBoardingUserDataResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull OnBoardingUserDataResponse item) {
                mLootSDK.signup().saveOnboardingUserData(OnBoardingUserDataResponse.parseToDataObject(item));
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<OnBoardingUserDataResponse>> createCall() {
                return apiInterface.uploadNotificationsToken(request);
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> registerFromLogin(String cloudMessagingToken, String deviceId) {
        final LoginPushNotificationRequest request = new LoginPushNotificationRequest(cloudMessagingToken, deviceId);
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkBoundAction<ResponseBody>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ResponseBody item) {
                // no-op
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return apiInterface.sendNotificationToken(request);
            }
        }.asLiveData();
    }

}
