package io.loot.lootsdk;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import io.loot.lootsdk.database.daos.AuthDeviceDao;
import io.loot.lootsdk.database.daos.SessionDao;
import io.loot.lootsdk.exceptions.IncorrectPinException;
import io.loot.lootsdk.exceptions.NoPinOnDeviceException;
import io.loot.lootsdk.exceptions.NoTouchOnDeviceException;
import io.loot.lootsdk.models.ActionResult;
import io.loot.lootsdk.models.Resource;
import io.loot.lootsdk.models.data.authToken.AuthorizedDevice;
import io.loot.lootsdk.models.data.userinfo.AccountStatus;
import io.loot.lootsdk.models.data.userinfo.Session;
import io.loot.lootsdk.models.entities.SessionRoomEntity;
import io.loot.lootsdk.models.networking.ErrorExtractor;
import io.loot.lootsdk.models.networking.authToken.AuthDeviceResponse;
import io.loot.lootsdk.models.networking.authToken.AuthDevicesResponse;
import io.loot.lootsdk.models.networking.authToken.AuthTokenResponse;
import io.loot.lootsdk.models.networking.authToken.DeleteAuthTokenRequest;
import io.loot.lootsdk.models.networking.sessions.AccountStatusResponse;
import io.loot.lootsdk.models.networking.sessions.LoginRequest;
import io.loot.lootsdk.models.networking.sessions.LoginResponse;
import io.loot.lootsdk.models.networking.signup.AccountStatusRequest;
import io.loot.lootsdk.models.orm.AuthDeviceEntity;
import io.loot.lootsdk.models.orm.DaoSession;
import io.loot.lootsdk.networking.ApiResponse;
import io.loot.lootsdk.networking.LootApiInterface;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import io.loot.lootsdk.utils.AppExecutors;
import io.loot.lootsdk.utils.DBHelper;
import io.loot.lootsdk.utils.KeyStoreUtil;
import okhttp3.ResponseBody;

public class Sessions implements Observer<SessionRoomEntity> {

    private final String ACCOUNT_STATUS_ONBOARDING = "ONBOARDING";

    private LootSDK mLootSDK;
    private SessionDao mSessionDao;
    private AuthDeviceDao mAuthDeviceDao;
    private KeyStoreUtil mKeyStoreUtil;
    private DaoSession mDaoSession;
    private SessionRoomEntity mEntity;

    Sessions(LootSDK lootSDK) {
        mLootSDK = lootSDK;

        mSessionDao = mLootSDK.getDatabase().sessionDao();
        mDaoSession = DBHelper.getInstance().getSession(false);
        mAuthDeviceDao = mLootSDK.getDatabase().authDeviceDao();
        mKeyStoreUtil = KeyStoreUtil.getInstance(lootSDK.getContext());
    }

    SessionRoomEntity getSessionEntity() {
        if (mEntity != null) {
            return mEntity;
        }

        return new SessionRoomEntity();
    }

    void fetch() {
        LiveData<SessionRoomEntity> entityLiveData = mSessionDao.getSession();
        entityLiveData.observeForever(this);
    }

    void saveEmail(String email) {
        SessionRoomEntity entity = getSessionEntity();
        entity.setEmail(email);

        saveSession(entity);
    }

    void saveIntercomHash(String hash) {
        SessionRoomEntity entity = getSessionEntity();
        entity.setIntercomHash(hash);

        saveSession(entity);
    }

    public boolean isPinLoginAvailable() {
        if (mKeyStoreUtil.getPinAuthToken() == null || mKeyStoreUtil.getPin() == null) {
            return false;
        }

        return (!mKeyStoreUtil.getPinAuthToken().isEmpty() && !mKeyStoreUtil.getPin().isEmpty());
    }

    public void disablePINLogin() {
        mKeyStoreUtil.savePinAuthToken("");
        mKeyStoreUtil.savePin("");
    }

    public boolean isTouchLoginAvailable() {
        return mKeyStoreUtil.getTouchAuthToken() != null && (!mKeyStoreUtil.getTouchAuthToken().isEmpty());
    }

    public void disableTouchLogin() {
        mKeyStoreUtil.saveTouchAuthToken("");
    }

    public boolean isUserLoggedIn() {
        String sessionEntityToken = getSessionEntity().getAuthorizationToken();
        String sharedPrefsToken = mKeyStoreUtil.getLoginToken();

        return (!sessionEntityToken.isEmpty() || !sharedPrefsToken.isEmpty());
    }

    public LiveData<Session> getSession() {
        return Transformations.map(mSessionDao.getSession(), new Function<SessionRoomEntity, Session>() {
            @Override
            public Session apply(SessionRoomEntity input) {
                if (input == null) {
                    return null;
                }

                return SessionRoomEntity.parseToDataObject(input);
            }
        });
    }

    public String getUserEmail() {
        return getSessionEntity().getEmail();
    }

    public String getUserAuthorizationToken() {
        return getSessionEntity().getAuthorizationToken();
    }

    public String getUserIntercomHash() {
        return getSessionEntity().getIntercomHash();
    }

    public String getUserId() {
        return getSessionEntity().getUserId();
    }

    public void clear() {
        if (mSessionDao != null) {
            mSessionDao.deleteAll();
        }
        if (mDaoSession != null && mDaoSession.getSessionEntityDao() != null) {
            mDaoSession.getSessionEntityDao().deleteAll();
            mDaoSession.clear();
        }
    }

    public LiveData<Resource<AccountStatus>> checkAccountStatus(final String email, final String password) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.PLATFORM_HEADER, header).getApiService();

        return new NetworkResource<AccountStatus, AccountStatusResponse>(AppExecutors.get()) {
            @Override
            protected AccountStatus proceedData(@NonNull AccountStatusResponse item) {
                String status = item.getStatus();
                return ACCOUNT_STATUS_ONBOARDING.equals(status) ? AccountStatus.ONBOARDING : AccountStatus.CREATED;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<AccountStatusResponse>> createCall() {
                return apiInterface.getAccountStatus(new AccountStatusRequest(email, password));
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> login(final String email, final String password) {
        LootHeader lootHeader = mLootSDK.createLootHeader();
        final LootApiInterface lootApiInterface = mLootSDK.createRestClient(InterceptorType.NO_TOKEN, lootHeader).getApiService();

        final LiveData<ActionResult> loginResult = new NetworkBoundAction<LoginResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull LoginResponse item) {
                saveSession(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<LoginResponse>> createCall() {
                return lootApiInterface.login(new LoginRequest(email, password));
            }
        }.asLiveData();

        return mergeLoginResultWithAccountDetails(loginResult);
    }

    void saveSession(SessionRoomEntity sessionEntity) {
        fetch();
        mSessionDao.insert(sessionEntity);
    }

    private void saveSession(LoginResponse response) {
        mSessionDao.deleteAll();

        SessionRoomEntity sessionEntity = getSessionEntity();
        sessionEntity.setEmail(response.getEmail());
        sessionEntity.setAuthorizationToken(response.getToken());
        sessionEntity.setIntercomHash(response.getIntercomHash());
        sessionEntity.setUserId(response.getPublicId());

        mEntity = sessionEntity;
        saveSession(sessionEntity);
    }

    public LiveData<ActionResult> refreshSession(String password) {
        String email = getSessionEntity().getEmail();
        return login(email, password);
    }

    private List<AuthorizedDevice> parseDevicesEntitiesToDataObjects(List<AuthDeviceEntity> authDeviceEntities) {
        List<AuthorizedDevice> authorizedDevices = new ArrayList<>();

        if (authDeviceEntities == null) {
            return authorizedDevices;
        }

        for (AuthDeviceEntity deviceEntity : authDeviceEntities) {
            if (deviceEntity == null) {
                continue;
            }

            authorizedDevices.add(deviceEntity.parseToDataObject());
        }

        return authorizedDevices;
    }

    private List<AuthorizedDevice> parseDevicesResponsesToDataObjects(List<AuthDeviceResponse> authDeviceResponses) {
        List<AuthorizedDevice> authorizedDevices = new ArrayList<>();

        if (authDeviceResponses == null) {
            return authorizedDevices;
        }

        for (AuthDeviceResponse deviceResponse : authDeviceResponses) {
            if (deviceResponse == null) {
                continue;
            }

            authorizedDevices.add(AuthDeviceResponse.parseToDataObject(deviceResponse));
        }

        return authorizedDevices;
    }


    public LiveData<Resource<List<AuthorizedDevice>>> getAuthorizedDevices() {
        LootHeader lootHeader = mLootSDK.createLootHeader();
        final LootApiInterface lootApiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, lootHeader).getApiService();

        return new NetworkBoundResource<List<AuthorizedDevice>, AuthDevicesResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull AuthDevicesResponse item) {
                mAuthDeviceDao.deleteAll();

                for (AuthDeviceResponse response : item.getAuthDevices()) {
                    if (response == null) {
                        continue;
                    }

                    mAuthDeviceDao.insert(AuthDeviceResponse.parseToEntityObject(response));
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<AuthorizedDevice> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<AuthorizedDevice>> loadFromDb() {
                return Transformations.map(mAuthDeviceDao.loadAll(), new Function<List<AuthDeviceEntity>, List<AuthorizedDevice>>() {
                    @Override
                    public List<AuthorizedDevice> apply(List<AuthDeviceEntity> input) {
                        return parseDevicesEntitiesToDataObjects(input);
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<AuthDevicesResponse>> createCall() {
                return lootApiInterface.getAuthDevices();
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> deleteAuthorizedDevice(final String deviceID) {
        LootHeader lootHeader = mLootSDK.createLootHeader();
        final LootApiInterface lootApiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, lootHeader).getApiService();

        return new NetworkBoundAction<ResponseBody>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ResponseBody item) {
                mAuthDeviceDao.deleteById(deviceID);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return lootApiInterface.deleteAuthToken(new DeleteAuthTokenRequest(deviceID));
            }
        }.asLiveData();
    }

    private LiveData<Resource<String>> createAuthDevice(String deviceID, String deviceName) {
        LootHeader lootHeader = mLootSDK.createLootHeader();
        final LootApiInterface lootApiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, lootHeader).getApiService();

        final AuthDeviceResponse authDeviceResponse = new AuthDeviceResponse(deviceName, deviceID);
        return new NetworkResource<String, AuthTokenResponse>(AppExecutors.get()) {
            @Override
            protected String proceedData(@NonNull AuthTokenResponse item) {
                return item.getAuthToken();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<AuthTokenResponse>> createCall() {
                return lootApiInterface.createAuthToken(authDeviceResponse);
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> createPIN(final String pin, String deviceID, String deviceName) {
        return Transformations.map(createAuthDevice(deviceID, deviceName), new Function<Resource<String>, ActionResult>() {
            @Override
            public ActionResult apply(Resource<String> input) {
                if (input.isSuccessful() && input.isLive()) {
                    mKeyStoreUtil.savePin(pin);
                    mKeyStoreUtil.savePinAuthToken(input.getData());
                    return ActionResult.success();
                }

                if (input.isLoading()) {
                    return ActionResult.loading();
                }

                if (!input.isSuccessful()) {
                    return ActionResult.error(input.getErrorMessage());
                }

                return ActionResult.error(ErrorExtractor.UNEXPECTED_ERROR);
            }
        });
    }

    public LiveData<ActionResult> loginByPIN(String pin) throws IncorrectPinException {
        if (mKeyStoreUtil.getPinAuthToken() == null || mKeyStoreUtil.getPin() == null ||
                mKeyStoreUtil.getPinAuthToken().isEmpty() || mKeyStoreUtil.getPin().isEmpty()) {
            throw new NoPinOnDeviceException();
        }

        if (!pin.equals(mKeyStoreUtil.getPin())) {
            throw new IncorrectPinException();
        }

        LootHeader lootHeader = mLootSDK.createLootHeader();
        final LootApiInterface lootApiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, lootHeader).getApiService();

        final LiveData<ActionResult> loginResult = new NetworkBoundAction<LoginResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull LoginResponse item) {
                saveSession(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<LoginResponse>> createCall() {
                return lootApiInterface.loginViaPin(mKeyStoreUtil.getPinAuthToken());
            }
        }.asLiveData();

        return mergeLoginResultWithAccountDetails(loginResult);
    }

    private LiveData<ActionResult> mergeLoginResultWithAccountDetails(final LiveData<ActionResult> loginResult) {
        final MediatorLiveData<ActionResult> resultLiveData = new MediatorLiveData<>();
        resultLiveData.addSource(loginResult, new Observer<ActionResult>() {
            @Override
            public void onChanged(@Nullable ActionResult result) {
                if (result == null) {
                    return;
                }

                if (result.isSuccess() && !result.isLoading()) {
                    resultLiveData.removeSource(loginResult);
                    resultLiveData.addSource(mLootSDK.user().fetchAccountDetails(), new Observer<ActionResult>() {
                        @Override
                        public void onChanged(@Nullable ActionResult result) {
                            if (result == null) {
                                return;
                            }

                            resultLiveData.setValue(result);
                        }
                    });
                } else {
                    resultLiveData.setValue(result);
                }
            }
        });

        return resultLiveData;
    }

    public LiveData<ActionResult> createTouchDevice(String deviceID, String deviceName) {
        return Transformations.map(createAuthDevice(deviceID, deviceName), new Function<Resource<String>, ActionResult>() {
            @Override
            public ActionResult apply(Resource<String> input) {
                if (input.isSuccessful() && input.isLive()) {
                    mKeyStoreUtil.saveTouchAuthToken(input.getData());
                    return ActionResult.success();
                }

                if (input.isLoading()) {
                    return ActionResult.loading();
                }

                if (!input.isSuccessful()) {
                    return ActionResult.error(input.getErrorMessage());
                }

                return ActionResult.error(ErrorExtractor.UNEXPECTED_ERROR);
            }
        });
    }

    public LiveData<ActionResult> loginByTouch() {
        if (mKeyStoreUtil.getTouchAuthToken() == null ||
                mKeyStoreUtil.getTouchAuthToken().isEmpty()) {
            throw new NoTouchOnDeviceException();
        }

        LootHeader lootHeader = mLootSDK.createLootHeader();
        final LootApiInterface lootApiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, lootHeader).getApiService();

        LiveData<ActionResult> loginResult = new NetworkBoundAction<LoginResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull LoginResponse item) {
                saveSession(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<LoginResponse>> createCall() {
                return lootApiInterface.loginViaTouchId(mKeyStoreUtil.getTouchAuthToken());
            }
        }.asLiveData();

        return mergeLoginResultWithAccountDetails(loginResult);
    }

    public void clearCached() {
        mEntity = null;
    }

    @Override
    public void onChanged(@Nullable SessionRoomEntity sessionRoomEntity) {
        mEntity = sessionRoomEntity;

        if (sessionRoomEntity != null && !sessionRoomEntity.getAuthorizationToken().isEmpty()) {
            mKeyStoreUtil.saveLoginToken(sessionRoomEntity.getAuthorizationToken());
        }
    }
}
