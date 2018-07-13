package io.loot.lootsdk;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.loot.lootsdk.database.daos.ForgottenPasswordDao;
import io.loot.lootsdk.models.ActionResult;
import io.loot.lootsdk.models.Resource;
import io.loot.lootsdk.models.data.forgottenPassword.ForgotPasswordMethod;
import io.loot.lootsdk.models.data.forgottenPassword.PhoneValidationNameAndCode;
import io.loot.lootsdk.models.data.forgottenPassword.ProvidePasswordMethod;
import io.loot.lootsdk.models.networking.forgottenPassword.ForgotPasswordConfirmEmailRequest;
import io.loot.lootsdk.models.networking.forgottenPassword.ForgotPasswordConfirmRequest;
import io.loot.lootsdk.models.networking.forgottenPassword.ForgotPasswordMethodResponse;
import io.loot.lootsdk.models.networking.forgottenPassword.PhoneInvalidRequest;
import io.loot.lootsdk.models.networking.forgottenPassword.PhoneValidationNameAndCodeResponse;
import io.loot.lootsdk.models.networking.forgottenPassword.ProvidePasswordMethodResponse;
import io.loot.lootsdk.models.networking.forgottenPassword.ProvidePasswordRequest;
import io.loot.lootsdk.models.networking.forgottenPassword.ResetPasswordRequest;
import io.loot.lootsdk.models.orm.ForgottenPasswordEntity;
import io.loot.lootsdk.networking.ApiResponse;
import io.loot.lootsdk.networking.LootApiInterface;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import io.loot.lootsdk.utils.AppExecutors;
import okhttp3.ResponseBody;

public class ForgottenPassword implements Observer<ForgottenPasswordEntity> {

    private LootSDK mLootSDK;
    private ForgottenPasswordDao mForgottenPasswordDao;
    private ForgottenPasswordEntity mCurrentForgottenPasswordEntity;

    ForgottenPassword(LootSDK lootSDK) {
        mLootSDK = lootSDK;
        mForgottenPasswordDao = mLootSDK.getDatabase().forgottenPasswordDao();
        mCurrentForgottenPasswordEntity = getForgottenPasswordEntity();
    }

    void fetch() {
        mForgottenPasswordDao.load().observeForever(this);
    }

    public LiveData<Resource<PhoneValidationNameAndCode>> checkPhoneValidation() {
        LootHeader header = getForgottenPasswordHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkResource<PhoneValidationNameAndCode, PhoneValidationNameAndCodeResponse>(AppExecutors.get()) {
            @Override
            protected PhoneValidationNameAndCode proceedData(@NonNull PhoneValidationNameAndCodeResponse item) {
                return PhoneValidationNameAndCodeResponse.parseToDataObject(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<PhoneValidationNameAndCodeResponse>> createCall() {
                return apiInterface.checkPhoneValidation();
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> setPhoneInvalid() {
        LootHeader header = getForgottenPasswordHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkBoundAction<ResponseBody>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ResponseBody item) {
                // nothing to do with it
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                PhoneInvalidRequest phoneInvalidRequest = new PhoneInvalidRequest();
                phoneInvalidRequest.setEmail(mCurrentForgottenPasswordEntity.getEmail());
                return apiInterface.setPhoneInvalid(phoneInvalidRequest);
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> confirmPasswordReset(final String code, final String token) {
        LootHeader header = getForgottenPasswordHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkBoundAction<ResponseBody>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ResponseBody item) {
                // nothing to do with it
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                ForgotPasswordConfirmRequest forgotPasswordConfirmRequest = new ForgotPasswordConfirmRequest();
                forgotPasswordConfirmRequest.setCode(code);
                forgotPasswordConfirmRequest.setToken(token);

                return apiInterface.resetPasswordConfirm(forgotPasswordConfirmRequest);
            }
        }.asLiveData();
    }


    public LiveData<ActionResult> confirmPasswordResetEmail(final String email, final String token) {
        LootHeader header = getForgottenPasswordHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkBoundAction<ResponseBody>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ResponseBody item) {
                // nothing to do with it
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                ForgotPasswordConfirmEmailRequest forgotPasswordConfirmEmailRequest = new ForgotPasswordConfirmEmailRequest();
                forgotPasswordConfirmEmailRequest.setEmail(email);
                forgotPasswordConfirmEmailRequest.setToken(token);

                return apiInterface.resetPasswordConfirmEmail(forgotPasswordConfirmEmailRequest);
            }
        }.asLiveData();
    }


    public LiveData<Resource<ForgotPasswordMethod>> resetPassword(final String email) {
        LootHeader header = getForgottenPasswordHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkResource<ForgotPasswordMethod, ForgotPasswordMethodResponse>(AppExecutors.get()) {
            @Override
            protected ForgotPasswordMethod proceedData(@NonNull ForgotPasswordMethodResponse item) {

                AppExecutors.get().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        setUserEmail(email);
                    }
                });

                return ForgotPasswordMethodResponse.parseToDataObject(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ForgotPasswordMethodResponse>> createCall() {
                ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
                resetPasswordRequest.setEmail(email);

                return apiInterface.resetPassword(resetPasswordRequest);
            }
        }.asLiveData();
    }

    public LiveData<Resource<ProvidePasswordMethod>> provideNewPassword(final String email, final String password) {
        LootHeader header = getForgottenPasswordHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkResource<ProvidePasswordMethod, ProvidePasswordMethodResponse>(AppExecutors.get()) {
            @Override
            protected ProvidePasswordMethod proceedData(@NonNull ProvidePasswordMethodResponse item) {
                return ProvidePasswordMethodResponse.parseToDataObject(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ProvidePasswordMethodResponse>> createCall() {
                ProvidePasswordRequest providePasswordRequest = new ProvidePasswordRequest();
                providePasswordRequest.setEmail(email);
                providePasswordRequest.setPassword(password);

                return apiInterface.providePassword(providePasswordRequest);
            }
        }.asLiveData();
    }

    private ForgottenPasswordEntity getForgottenPasswordEntity() {
        ForgottenPasswordEntity entity = new ForgottenPasswordEntity();
        if (mCurrentForgottenPasswordEntity != null) {
            entity = mCurrentForgottenPasswordEntity;
        }

        return entity;
    }

    private String getUserEmail() {
        if (mCurrentForgottenPasswordEntity == null) {
            mCurrentForgottenPasswordEntity = getForgottenPasswordEntity();
        }

        String email = mCurrentForgottenPasswordEntity.getEmail();
        if (email == null) {
            email = "";
            setUserEmail(email);
        }

        return email;
    }

    private void setUserEmail(String email) {
        if (mCurrentForgottenPasswordEntity == null) {
            mCurrentForgottenPasswordEntity = getForgottenPasswordEntity();
        }

        mCurrentForgottenPasswordEntity.setEmail(email);
        saveForgottenPasswordEntity(mCurrentForgottenPasswordEntity);
    }


    private void saveForgottenPasswordEntity(ForgottenPasswordEntity forgottenPasswordEntity) {
        mForgottenPasswordDao.insert(forgottenPasswordEntity);
        mCurrentForgottenPasswordEntity = forgottenPasswordEntity;
    }

    private LootHeader getForgottenPasswordHeader() {
        LootHeader header = mLootSDK.createLootHeader();
        header.setEmail(getUserEmail());
        header.setAuthToken("");
        header.setOnboardingToken("");
        return header;
    }

    public void clear() {
        AppExecutors.get().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                mForgottenPasswordDao.load().removeObserver(ForgottenPassword.this);
            }
        });

        mCurrentForgottenPasswordEntity = null;
    }

    @Override
    public void onChanged(@Nullable ForgottenPasswordEntity forgottenPasswordEntity) {
        mCurrentForgottenPasswordEntity = forgottenPasswordEntity;
    }
}
