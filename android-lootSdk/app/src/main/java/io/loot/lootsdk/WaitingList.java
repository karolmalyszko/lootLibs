package io.loot.lootsdk;

import com.google.gson.Gson;

import io.loot.lootsdk.exceptions.NullPointerListenerException;
import io.loot.lootsdk.listeners.pushNotifications.RegisterNotificationListener;
import io.loot.lootsdk.listeners.signup.RegistrationListener;
import io.loot.lootsdk.listeners.waitingList.GetPositionDetailsListener;
import io.loot.lootsdk.listeners.waitingList.RegisterToWaitingListListener;
import io.loot.lootsdk.listeners.waitingList.ResendEmailListener;
import io.loot.lootsdk.models.entities.SessionRoomEntity;
import io.loot.lootsdk.models.networking.ErrorExtractor;
import io.loot.lootsdk.models.networking.waitingList.PositionDetailsResponse;
import io.loot.lootsdk.models.networking.waitingList.SignUpWaitingListError;
import io.loot.lootsdk.models.networking.waitingList.SignUpWaitingListRequest;
import io.loot.lootsdk.models.networking.waitingList.WaitingListNotificationTokenRequest;
import io.loot.lootsdk.models.networking.waitingList.WaitingListResendEmailRequest;
import io.loot.lootsdk.models.orm.DaoSession;
import io.loot.lootsdk.models.orm.SessionEntity;
import io.loot.lootsdk.networking.LootApiInterface;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import io.loot.lootsdk.utils.DBHelper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WaitingList {

    private DaoSession mDaoSession;
    private LootSDK mLootSDK;

    WaitingList(LootSDK lootSDK) {
        mLootSDK = lootSDK;
        mDaoSession = DBHelper.getInstance().getSession(false);
    }

    public String getWaitingListToken() {
        SessionRoomEntity entity = mLootSDK.sessions().getSessionEntity();

        return entity.getWaitingListToken();
    }

    public boolean isOnWaitingList() {
        return !getWaitingListToken().isEmpty();
    }

    public void saveWaitingListToken(String token) {
        SessionEntity entity = mDaoSession.getSessionEntityDao().loadByRowId(0);
        entity.setWaitingListToken(token);
        mDaoSession.getSessionEntityDao().insertOrReplace(entity);
    }

    public void startSignUpFromWaitingList(String email, String password, String phoneNumber, String waitingListToken, RegistrationListener listener) {
        if (listener == null) {
            throw new NullPointerListenerException();
        }

        mLootSDK.signup().registerFromWaitingList(email, password, phoneNumber, waitingListToken, listener);
    }

    public void registerToWaitingList(String email, String refferalCode, final RegisterToWaitingListListener listener) {
        if (listener == null) {
            throw new NullPointerListenerException();
        }

        LootHeader header = mLootSDK.createLootHeader();
        LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_HEADER, header).getApiService();

        SignUpWaitingListRequest request = new SignUpWaitingListRequest(email, refferalCode);
        Call<ResponseBody> call = apiInterface.signUpToWaitingList(request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    listener.onRegisteredSuccessfully();
                    return;
                }

                Gson gson = new Gson();
                try {
                    String jsonString = response.errorBody().string();
                    SignUpWaitingListError error = gson.fromJson(jsonString, SignUpWaitingListError.class);

                    if (error.isAlreadyOnWaitingList()) {
                        listener.onAlreadyOnWaitingList();
                        return;
                    }

                    if (error.isRegularAccount()) {
                        listener.onRegisteredAsRegularAccount();
                        return;
                    }

                    listener.onRegisterError(error.getErrorTextMessage());

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public void getDetails(final GetPositionDetailsListener listener) {
        if (listener == null) {
            throw new NullPointerListenerException();
        }

        LootHeader header = mLootSDK.createLootHeader();
        LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_HEADER, header).getApiService();

        Call<PositionDetailsResponse> call = apiInterface.getWaitingListPosition(getWaitingListToken());
        call.enqueue(new Callback<PositionDetailsResponse>() {
            @Override
            public void onResponse(Call<PositionDetailsResponse> call, Response<PositionDetailsResponse> response) {
                if (response.isSuccessful()) {
                    listener.onDetailsReceived(PositionDetailsResponse.parseToDataObject(response.body()));
                    return;
                }


            }

            @Override
            public void onFailure(Call<PositionDetailsResponse> call, Throwable t) {

            }
        });
    }

    public void registerPushNotification(String deviceId, String token, final RegisterNotificationListener listener) {
        if (listener == null) {
            throw new NullPointerListenerException();
        }

        LootHeader header = mLootSDK.createLootHeader();
        LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_HEADER, header).getApiService();

        WaitingListNotificationTokenRequest request = new WaitingListNotificationTokenRequest(getWaitingListToken(), token, deviceId);
        Call<ResponseBody> call = apiInterface.signupToWaitingListPushNotification(request);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    listener.onRegisterNotificationSuccess();
                    return;
                }

                listener.onRegisterNotificationError(ErrorExtractor.UNEXPECTED_ERROR);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    public void resendEmail(String email, final ResendEmailListener listener) {
        if (listener == null) {
            throw new NullPointerListenerException();
        }

        LootHeader header = mLootSDK.createLootHeader();
        LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_HEADER, header).getApiService();
        Call<ResponseBody> call = apiInterface.resendVerificationEmailWaitingList(new WaitingListResendEmailRequest(email));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    listener.onResendEmailSuccess();
                    return;
                }

                listener.onResendEmailError(ErrorExtractor.UNEXPECTED_ERROR);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

}
