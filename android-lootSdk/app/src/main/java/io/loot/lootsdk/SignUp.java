package io.loot.lootsdk;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.loot.lootsdk.database.daos.OnboardingUserDataDao;
import io.loot.lootsdk.exceptions.NullPointerListenerException;
import io.loot.lootsdk.listeners.signup.RegistrationListener;
import io.loot.lootsdk.models.ActionResult;
import io.loot.lootsdk.models.Resource;
import io.loot.lootsdk.models.data.signup.FailedScansContainer;
import io.loot.lootsdk.models.data.userinfo.Kyc;
import io.loot.lootsdk.models.data.userinfo.OnBoardingAddress;
import io.loot.lootsdk.models.data.userinfo.OnBoardingUserData;
import io.loot.lootsdk.models.data.userinfo.PersonalData;
import io.loot.lootsdk.models.data.userinfo.UserAccountData;
import io.loot.lootsdk.models.entities.OnBoardingUserDataRoomEntity;
import io.loot.lootsdk.models.networking.signup.AccountStatusRequest;
import io.loot.lootsdk.models.networking.signup.ConfirmDataResponse;
import io.loot.lootsdk.models.networking.signup.OnBoardingUserDataResponse;
import io.loot.lootsdk.models.networking.signup.PersonalDataResponse;
import io.loot.lootsdk.models.networking.signup.PhoneNumberRequest;
import io.loot.lootsdk.models.networking.signup.SignupRequest;
import io.loot.lootsdk.models.networking.signup.SignupResponse;
import io.loot.lootsdk.models.networking.signup.StartKYCRequest;
import io.loot.lootsdk.networking.ApiResponse;
import io.loot.lootsdk.networking.LootApiInterface;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import io.loot.lootsdk.utils.AppExecutors;
import io.loot.lootsdk.utils.KeyStoreUtil;
import okhttp3.ResponseBody;

public class SignUp implements Observer<OnBoardingUserDataRoomEntity> {

    public final static String SIGN_UP = "SIGN_UP"; // -1
    public final static String MOBILE_NUMBER_VERIFICATION = "MOBILE_NUMBER_VERIFICATION"; // 0 position at Array
    public final static String IDENTITY_VERIFICATION = "IDENTITY_VERIFICATION"; // 1, jumio
    public final static String ADDRESS_FINDER = "ADDRESS_FINDER"; // 2
    public final static String PERSONAL_DATA = "PERSONAL_DATA"; // 3
    public final static String PROFILE_PHOTO = "PROFILE_PHOTO"; // 4
    public final static String CONFIRMATION = "CONFIRMATION"; // 5
    public final static String FINGERPRINT_OPTIONAL = "FINGERPRINT_OPTIONAL"; // 6
    public final static String SET_UP_PIN = "SET_UP_PIN"; // 7
    public final static String CONFIRM_PIN = "CONFIRM_PIN"; // 8
    public final static String SET_UP_PIN_SUCCESS = "SET_UP_PIN_SUCCESS"; // 9

    private LootSDK mLootSDK;
    private KeyStoreUtil mKeyStoreUtil;
    private OnboardingUserDataDao mOnboardingUserDataDao;

    private OnBoardingUserDataRoomEntity mCachedOnboardingData;

    SignUp(LootSDK lootSDK) {
        mLootSDK = lootSDK;
        mKeyStoreUtil = KeyStoreUtil.getInstance(mLootSDK.getContext());
        mOnboardingUserDataDao = lootSDK.getDatabase().onboardingUserDataDao();
    }

    public void fetch() {
        mOnboardingUserDataDao.load().observeForever(this);
    }

    private void setSessionData(ConfirmDataResponse response) {
        mLootSDK.sessions().saveSession(ConfirmDataResponse.parseToEntityObject(response));
    }

    public void clearOnBoardingData() {
        AppExecutors.get().mainThread().execute(new Runnable() {
            @Override
            public void run() {
                mOnboardingUserDataDao.load().removeObserver(SignUp.this);
            }
        });

        mOnboardingUserDataDao.deleteAll();
        mKeyStoreUtil.saveInternalStep("");
        mKeyStoreUtil.saveOnBoardingToken("");
    }

    public void clearCached() {
        mCachedOnboardingData = null;
    }

    private OnBoardingUserData changeStepIfHasPin(OnBoardingUserData data) {
        if (data.getLastFinishedStep().equals(CONFIRMATION)
                && ((mLootSDK.sessions().isPinLoginAvailable() || mLootSDK.sessions().isTouchLoginAvailable() || mKeyStoreUtil.wasPinSkipped()))) {
            data.setLastFinishedStep(CONFIRM_PIN);
        }

        return data;
    }


    public void persistInternalStep(String internalStepName) {
        mKeyStoreUtil.saveInternalStep(internalStepName);
    }

    public String getInternalStep() {
        return mKeyStoreUtil.getInternalStep();
    }

    void saveOnboardingUserData(OnBoardingUserData userData) {
        if (userData.getPersonalData().getAddress().equals(new OnBoardingAddress()) && ADDRESS_FINDER.equals(getOnBoardingData().getLastFinishedStep())) {
            userData.setLastFinishedStep(ADDRESS_FINDER);
            userData.getPersonalData().setAddress(getOnBoardingData().getPersonalData().getAddress());

            if (userData.getPersonalData().getTitle().isEmpty() || userData.getPersonalData().getGender().isEmpty()) {
                userData.getPersonalData().setTitle(getOnBoardingData().getPersonalData().getTitle());
                userData.getPersonalData().setGender(getOnBoardingData().getPersonalData().getGender());
            }

            if (userData.getPersonalData().getPreferredFistName().isEmpty() || userData.getPersonalData().getPreferredLastName().isEmpty()) {
                userData.getPersonalData().setPreferredFistName(getOnBoardingData().getPersonalData().getPreferredFistName());
                userData.getPersonalData().setPreferredLastName(getOnBoardingData().getPersonalData().getPreferredLastName());
            }
        }


        if (userData.getPersonalData().getAddress().toString().trim().isEmpty()) {
            if (userData.getPersonalData() != null && getOnBoardingData() != null && getOnBoardingData().getPersonalData() != null) {
                userData.getPersonalData().setAddress(getOnBoardingData().getPersonalData().getAddress());
            }
        }

        final OnBoardingUserDataRoomEntity entity = OnBoardingUserDataRoomEntity.parseToEntity(changeStepIfHasPin(userData));

        mCachedOnboardingData = entity;
        AppExecutors.get().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mOnboardingUserDataDao.insert(entity);
            }
        });
    }

    public LiveData<OnBoardingUserData> getCachedUserData() {
        return Transformations.map(mOnboardingUserDataDao.load(), new Function<OnBoardingUserDataRoomEntity, OnBoardingUserData>() {
            @Override
            public OnBoardingUserData apply(OnBoardingUserDataRoomEntity input) {
                return input.parseToDataObject();
            }
        });
    }

    private OnBoardingUserData getOnBoardingData() {
        OnBoardingUserData onBoardingUserData = new OnBoardingUserData();

        if (mCachedOnboardingData != null) {
            onBoardingUserData = mCachedOnboardingData.parseToDataObject();
        }

        if (!getInternalStep().isEmpty()) {
            onBoardingUserData.setLastFinishedStep(getInternalStep());
        }

        return onBoardingUserData;
    }

    public String getUserEmail() {
        return getOnBoardingData().getEmail();
    }

    public String getLastFinishedStep() {
        return getOnBoardingData().getLastFinishedStep();
    }

    @Deprecated
    public OnBoardingUserData getUserData() {
        return getOnBoardingData();
    }

    public UserAccountData getAccountData() {
        return new UserAccountData();
    }

    public boolean isSigningUp() {
        return !getToken().isEmpty() || !mKeyStoreUtil.getOnBoardingToken().isEmpty();
    }

    public String getUserId() {
        return getOnBoardingData().getPublicId();
    }

    String getToken() {
        return getOnBoardingData().getToken();
    }

    private void saveResponse(SignupResponse response, String phoneNumber, String email) {
        OnBoardingUserData onboardingUserData = new OnBoardingUserData();
        OnBoardingAddress address = new OnBoardingAddress();
        Kyc kyc = new Kyc();

        PersonalData personalData = new PersonalData();
        personalData.setAddress(address);

        onboardingUserData.setKyc(kyc);
        onboardingUserData.setLastFinishedStep(response.getLastFinishedStep());
        onboardingUserData.setPersonalData(personalData);
        onboardingUserData.setPhoneNumber(phoneNumber);
        onboardingUserData.setEmail(email);
        onboardingUserData.setToken(response.getToken());
        onboardingUserData.setIntercomHash(response.getIntercomHash());
        onboardingUserData.setPublicId(response.getPublicId());

        saveOnboardingUserData(onboardingUserData);

        mLootSDK.sessions().saveEmail(email);
        mLootSDK.sessions().saveIntercomHash(response.getIntercomHash());

    }

    public void updateOnBoardingData(OnBoardingUserData onBoardingUserData) {
        saveOnboardingUserData(onBoardingUserData);
    }

    public LiveData<ActionResult> startRegistration(final String email, final String password, final String phoneNumber) {
        SignupRequest request = new SignupRequest(email, password, phoneNumber);
        return register(request);
    }

    private LiveData<ActionResult> mergeWithOnboardingData(LiveData<ActionResult> resultLiveData) {
        final MediatorLiveData<ActionResult> mediatorLiveData = new MediatorLiveData<>();
        mediatorLiveData.addSource(resultLiveData, new Observer<ActionResult>() {
            @Override
            public void onChanged(@Nullable final ActionResult result) {
                if (result == null) {
                    return;
                }

                if (!result.isSuccess()) {
                    mediatorLiveData.setValue(result);
                    return;
                }

                mediatorLiveData.addSource(mOnboardingUserDataDao.load(), new Observer<OnBoardingUserDataRoomEntity>() {
                    @Override
                    public void onChanged(@Nullable OnBoardingUserDataRoomEntity onBoardingUserDataRoomEntity) {
                        if (onBoardingUserDataRoomEntity == null) {
                            return;
                        }

                        mCachedOnboardingData = onBoardingUserDataRoomEntity;
                        mediatorLiveData.setValue(result);
                    }
                });
            }
        });

        return mediatorLiveData;
    }

    private LiveData<ActionResult> register(final SignupRequest request) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkBoundAction<SignupResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull SignupResponse item) {
                saveResponse(item, request.getPhoneNumber(), request.getEmail());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SignupResponse>> createCall() {
                return apiInterface.signUp(request);
            }
        }.asLiveData();
    }

    LiveData<ActionResult> registerFromWaitingList(String email, String password, String phoneNumber, String waitingListToken, RegistrationListener listener) {
        if (listener == null) {
            throw new NullPointerListenerException();
        }

        final SignupRequest request = new SignupRequest(email, password, phoneNumber);
        request.setWaitingListToken(waitingListToken);

        return register(request);
    }

    public LiveData<ActionResult> continueRegistration(String password) {
        String email = getOnBoardingData().getEmail();
        return continueRegistration(email, password);
    }

    public LiveData<ActionResult> continueRegistration(final String email, final String password) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkBoundAction<OnBoardingUserDataResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull OnBoardingUserDataResponse item) {
                saveOnboardingUserData(OnBoardingUserDataResponse.parseToDataObject(item));
                mLootSDK.sessions().saveIntercomHash(item.getIntercomHash());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<OnBoardingUserDataResponse>> createCall() {
                return apiInterface.getInterruptedOnBoardingData(new AccountStatusRequest(email, password));
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> verifyPhoneNumber(final String verificationCode) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkBoundAction<SignupResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull SignupResponse item) {
                OnBoardingUserData data = getOnBoardingData();
                data.setLastFinishedStep(item.getLastFinishedStep());
                saveOnboardingUserData(data);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SignupResponse>> createCall() {
                return apiInterface.verifyPhoneNumber(verificationCode);
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> changePhoneNumber(final String newPhoneNumber) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkBoundAction<SignupResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull SignupResponse item) {
                OnBoardingUserData onBoardingUserData = getOnBoardingData();
                onBoardingUserData.setPhoneNumber(newPhoneNumber);
                saveOnboardingUserData(onBoardingUserData);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SignupResponse>> createCall() {
                return apiInterface.changePhoneNumber(new PhoneNumberRequest(newPhoneNumber));
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> resendVerificationSMS() {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkBoundAction<SignupResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull SignupResponse item) {
                // no-op
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SignupResponse>> createCall() {
                return apiInterface.resendVerificationSms();
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> startKYCVerification(final String reference) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkBoundAction<OnBoardingUserDataResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull OnBoardingUserDataResponse item) {
                OnBoardingUserData userData = OnBoardingUserDataResponse.parseToDataObject(item);
                saveOnboardingUserData(userData);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<OnBoardingUserDataResponse>> createCall() {
                return apiInterface.startKYCVerification(new StartKYCRequest(reference));
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> updatePersonalData(PersonalData personalData) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();
        final PersonalDataResponse request = PersonalDataResponse.parseToResponse(personalData);

        return new NetworkBoundAction<OnBoardingUserDataResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull OnBoardingUserDataResponse item) {
                saveOnboardingUserData(OnBoardingUserDataResponse.parseToDataObject(item));
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<OnBoardingUserDataResponse>> createCall() {
                return apiInterface.addPersonalData(request);
            }
        }.asLiveData();
    }

    public LiveData<Resource<ResponseBody>> getScannedImage() {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkResource<ResponseBody, ResponseBody>(AppExecutors.get()) {
            @Override
            protected ResponseBody proceedData(@NonNull ResponseBody item) {
                return item;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return apiInterface.getUserScanImage();
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> uploadProfilePhoto(final String base64Image) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkBoundAction<OnBoardingUserDataResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull OnBoardingUserDataResponse item) {
                OnBoardingUserData onBoardingUserData = OnBoardingUserDataResponse.parseToDataObject(item);
                saveOnboardingUserData(onBoardingUserData);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<OnBoardingUserDataResponse>> createCall() {
                return apiInterface.uploadProfilePhoto(base64Image);
            }
        }.asLiveData();
    }

    public LiveData<Resource<String>> checkKYCResult() {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkResource<String, OnBoardingUserDataResponse>(AppExecutors.get()) {
            @Override
            protected String proceedData(@NonNull OnBoardingUserDataResponse item) {
                return item.getKyc().getStatus();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<OnBoardingUserDataResponse>> createCall() {
                return apiInterface.getOnboardingUserData();
            }
        }.asLiveData();
    }

    public LiveData<Resource<FailedScansContainer>> getFailedScans() {
        final FailedScansContainer container = new FailedScansContainer();

        LiveData<Resource<ResponseBody>> idScanLiveData = getIDScan();
        final LiveData<Resource<ResponseBody>> backScanLiveData = getBackScan();
        final LiveData<Resource<ResponseBody>> faceScanLiveData = getFaceScan();

        final MediatorLiveData<Resource<FailedScansContainer>> mergedResult = new MediatorLiveData<>();
        mergedResult.addSource(idScanLiveData, new Observer<Resource<ResponseBody>>() {
            @Override
            public void onChanged(@Nullable Resource<ResponseBody> idScanResult) {
                if (idScanResult == null || (idScanResult.isLoading() && idScanResult.getData() == null)) {
                    return;
                }

                if (idScanResult.isSuccessful() && !idScanResult.isLoading()) {
                    container.setIdScanBody(idScanResult.getData());
                }

                mergedResult.addSource(backScanLiveData, new Observer<Resource<ResponseBody>>() {
                    @Override
                    public void onChanged(@Nullable Resource<ResponseBody> backScanResult) {
                        if (backScanResult == null || (backScanResult.isLoading() && backScanResult.getData() == null)) {
                            return;
                        }

                        if (backScanResult.isSuccessful() && !backScanResult.isLoading()) {
                            container.setBackIdScanBody(backScanResult.getData());
                        }

                        mergedResult.addSource(faceScanLiveData, new Observer<Resource<ResponseBody>>() {
                            @Override
                            public void onChanged(@Nullable Resource<ResponseBody> faceScanResult) {
                                if (faceScanResult == null || (faceScanResult.isLoading() && faceScanResult.getData() == null)) {
                                    return;
                                }

                                if (faceScanResult.isSuccessful() && !faceScanResult.isLoading()) {
                                    container.setFaceScanBody(faceScanResult.getData());
                                }

                                mergedResult.setValue(Resource.success(container));
                            }
                        });
                    }
                });
            }
        });

        return mergedResult;
    }

    private LiveData<Resource<ResponseBody>> getIDScan() {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkResource<ResponseBody, ResponseBody>(AppExecutors.get()) {
            @Override
            protected ResponseBody proceedData(@NonNull ResponseBody item) {
                return item;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return apiInterface.getUserIDImage();
            }
        }.asLiveData();
    }

    private LiveData<Resource<ResponseBody>> getBackScan() {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkResource<ResponseBody, ResponseBody>(AppExecutors.get()) {
            @Override
            protected ResponseBody proceedData(@NonNull ResponseBody item) {
                return item;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return apiInterface.getBackScan();
            }
        }.asLiveData();
    }

    private LiveData<Resource<ResponseBody>> getFaceScan() {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkResource<ResponseBody, ResponseBody>(AppExecutors.get()) {
            @Override
            protected ResponseBody proceedData(@NonNull ResponseBody item) {
                return item;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return apiInterface.getUserScanImage();
            }
        }.asLiveData();
    }

    public LiveData<Resource<ConfirmDataResponse>> confirmIdentity() {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_AND_TOKEN_HEADER, header).getApiService();

        return new NetworkResource<ConfirmDataResponse, ConfirmDataResponse>(AppExecutors.get()) {
            @Override
            protected ConfirmDataResponse proceedData(@NonNull ConfirmDataResponse item) {
                setSessionData(item);
                return item;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ConfirmDataResponse>> createCall() {
                return apiInterface.confirmData();
            }
        }.asLiveData();
    }

    public void skipPin() {
        OnBoardingUserData data = getOnBoardingData();
        data.setLastFinishedStep(CONFIRM_PIN);
        saveOnboardingUserData(data);

        mKeyStoreUtil.setPinWasSkipped();
    }

    public void persistAddress(OnBoardingAddress address) {
        OnBoardingUserData data = getOnBoardingData();
        if (data.getPersonalData() != null) {
            data.getPersonalData().setAddress(address);
        }

        saveOnboardingUserData(data);
    }

    public void dropBase() {
        AppExecutors.get().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mOnboardingUserDataDao.deleteAll();
                mCachedOnboardingData = null;
            }
        });
    }

    @Override
    public void onChanged(@Nullable OnBoardingUserDataRoomEntity onBoardingUserDataEntity) {
        mCachedOnboardingData = onBoardingUserDataEntity;

        if (onBoardingUserDataEntity != null && !onBoardingUserDataEntity.getToken().isEmpty()) {
            mKeyStoreUtil.saveOnBoardingToken(onBoardingUserDataEntity.getToken());
        }
    }
}
