package io.loot.lootsdk;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Base64;

import com.google.gson.Gson;

import io.loot.lootsdk.analytics.AnalyticsData;
import io.loot.lootsdk.database.LootDatabase;
import io.loot.lootsdk.database.LootDatabaseWrapper;
import io.loot.lootsdk.database.migrations.DatabaseMigrations;
import io.loot.lootsdk.exceptions.NotInitializedException;
import io.loot.lootsdk.listeners.DatabaseCreationListener;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import io.loot.lootsdk.networking.restClients.RestClient;
import io.loot.lootsdk.networking.restClients.RestClientFactory;
import io.loot.lootsdk.utils.AppExecutors;
import io.loot.lootsdk.utils.DBHelper;
import io.loot.lootsdk.utils.GreenDaoToRoomMigrationUtil;
import io.loot.lootsdk.utils.KeyStoreUtil;
import retrofit2.Call;

public class LootSDK {

    public static final int API_TYPE_PRODUCTION = 0;
    public static final int API_TYPE_STAGING = 1;
    public static final int API_TYPE_OFFLINE = 2;
    public static final int API_TYPE_PRE_PROD = 3;

    public static final String STAGING_URI = "https://api-staging.lootapp.io";
    public static final String PRODUCTION_URI = "https://api.lootapp.io";
    public static final String OFFLINE_URI = "https://api-staging.lootapp.io";
    public static final String PRE_PROD_API = "https://api-preprod.lootapp.io";
    private static LootSDK sInstance;
    private Context mContext;
    private boolean mIsInitialized;
    private String mAnalyticsDataBase64;
    private int mApiType = API_TYPE_STAGING; // staging by default
    private String mApiUri = STAGING_URI; // staging by default
    private String mAuthUsername = "lootapi"; // default one
    private String mAuthPassword = "c2b62473145461b23db1e973e475a4520c49a475"; // default one
    private Sessions mSessions;
    private User mUser;
    private FeesAndLimits mFeesAndLimits;
    private PolicyAndTerms mPolicyAndTerms;
    private Categories mCategories;
    private Cards mCards;
    private SignUp mSignUp;
    private PushNotification mPushNotification;
    private ForgottenPassword mForgottenPassword;
    private WaitingList mWaitingList;
    private Contacts mContacts;
    private ServerInfo mServerInfo;
    private Payments mPayments;
    private DBHelper mDBHelper;
    private LootSharedPreferences mLootSharedPreferences;

    private LootDatabaseWrapper mLootDatabase;

    private LootSDK() {
        mIsInitialized = false;
        mDBHelper = DBHelper.getInstance();
    }

    public static LootSDK getInstance() {
        if (sInstance == null) {
            sInstance = new LootSDK();
        }

        return sInstance;
    }

    private void init(int apiType, String authUsername, String authPassword) {
        fetchData();

        if (!authUsername.isEmpty()) {
            mAuthUsername = authUsername;
        }

        if (!authPassword.isEmpty()) {
            mAuthPassword = authPassword;
        }

        mApiType = apiType;
        setApiUri(mApiType);

        mIsInitialized = true;
    }

    public void fetchData() {
        sessions().fetch();
        user().fetch();
        signup().fetch();
        serverInfo().fetch();
        forgottenPassword().fetch();
    }

    private void init(final Context context, final int apiType, final String authUsername, final String authPassword, final DatabaseCreationListener listener, AnalyticsData analyticsData) {
        mContext = context;
        mAnalyticsDataBase64 = Base64.encodeToString(getAnalyticsJson(analyticsData).getBytes(), Base64.NO_WRAP);
        mDBHelper.init(context);

        if (listener != null) {
            initRoomDatabase(new DatabaseCreationListener() {
                @Override
                public void onDatabaseCreationResultReceived(boolean isSuccess) {
                    GreenDaoToRoomMigrationUtil.migrateIfNeeded(mDBHelper, mLootDatabase, KeyStoreUtil.getInstance(mContext));
                    init(apiType, authUsername, authPassword);

                    listener.onDatabaseCreationResultReceived(isSuccess);
                }
            });
            return;
        } else {
            initSupportRoomDatabase();
            GreenDaoToRoomMigrationUtil.migrateIfNeeded(mDBHelper, mLootDatabase, KeyStoreUtil.getInstance(mContext));
            init(apiType, authUsername, authPassword);
        }
    }

    private void initSupportRoomDatabase() {
        if (mLootDatabase == null) {
            LootDatabase database = Room.databaseBuilder(mContext, LootDatabase.class, "loot_sdk_db").allowMainThreadQueries().fallbackToDestructiveMigration()
                    .addMigrations(DatabaseMigrations.MIGRATION_31_32)
                    .addMigrations(DatabaseMigrations.MIGRATION_32_33)
                    .addMigrations(DatabaseMigrations.MIGRATION_33_34)
                    .build();
            mLootDatabase = new LootDatabaseWrapper(database);
        }
    }

    private void initRoomDatabase(DatabaseCreationListener listener) {
        if (mLootDatabase == null) {
            DatabaseCreatorAsyncTask databaseCreator = new DatabaseCreatorAsyncTask(listener);
            databaseCreator.execute();
        }
    }

    /**
     * Deprecated since 2.1.0, using LiveData will automatically
     * cancel every action when Fragment is not visible to user.
     * This returns always false!
     */
    @Deprecated
    public boolean isAnyCallExecuted() {
        return false;
    }

    @Deprecated
    public void init(Context context, int apiType, AnalyticsData analyticsData) {
        init(context, apiType, mAuthUsername, mAuthPassword, null, analyticsData);
    }

    @Deprecated
    public void init(Context context, String apiUrl, AnalyticsData analyticsData, int apiType) {
        init(context, apiType, mAuthUsername, mAuthPassword, null, analyticsData);
        setApiUri(apiUrl);
    }

    public void init(Context context, int apiType, AnalyticsData analyticsData, DatabaseCreationListener listener) {
        init(context, apiType, mAuthUsername, mAuthPassword, listener, analyticsData);
    }

    private void setApiUri(int apiType) {
        switch (apiType) {
            case API_TYPE_STAGING:
                mApiUri = STAGING_URI;
                break;
            case API_TYPE_PRODUCTION:
                mApiUri = PRODUCTION_URI;
                break;
            case API_TYPE_OFFLINE:
                mApiUri = OFFLINE_URI;
                break;
            case API_TYPE_PRE_PROD:
                mApiUri = PRE_PROD_API;
                break;
            default:
                mApiUri = STAGING_URI;
                break;
        }
    }

    /**
     * Deprecated since 2.1.0, using LiveData will automatically
     * cancel every action when Fragment is not visible to user.
     * This is no-op operation now.
     */
    @Deprecated
    synchronized public void cancelCalls() {
        // no-op due to LiveData migration
        // should be deleted in next versions
    }

    public Sessions sessions() {
        if (mSessions == null) {
            mSessions = new Sessions(this);
            mSessions.fetch();
        }

        return mSessions;
    }

    public User user() {
        if (mUser == null) {
            mUser = new User(this);
            mUser.fetch();
        }

        return mUser;
    }

    public Payments payments() {
        if(mPayments == null) {
            mPayments = new Payments(this);
        }

        return mPayments;
    }

    public ServerInfo serverInfo() {
        if (mServerInfo == null) {
            mServerInfo = new ServerInfo(this);
            mServerInfo.fetch();
        }

        return mServerInfo;
    }

    public WaitingList waitingList() {
        if (mWaitingList == null) {
            mWaitingList = new WaitingList(this);
        }

        return mWaitingList;
    }

    public FeesAndLimits feesAndLimits() {
        if (mFeesAndLimits == null) {
            mFeesAndLimits = new FeesAndLimits(this);
        }

        return mFeesAndLimits;
    }

    public PolicyAndTerms policyAndTerms() {
        if (mPolicyAndTerms == null) {
            mPolicyAndTerms = new PolicyAndTerms(this);
        }

        return mPolicyAndTerms;
    }

    public Categories categories() {
        if (mCategories == null) {
            mCategories = new Categories(this);
        }

        return mCategories;
    }

    public Cards cards() {
        if (mCards == null) {
            mCards = new Cards(this);
        }

        return mCards;
    }

    public SignUp signup() {
        if (mSignUp == null) {
            mSignUp = new SignUp(this);
            mSignUp.fetch();
        }

        return mSignUp;
    }

    public Contacts contacts() {
        if (mContacts == null) {
            mContacts = new Contacts(this);
        }
        return mContacts;
    }

    public PushNotification pushNotification() {
        if (mPushNotification == null) {
            mPushNotification = new PushNotification(this);
        }

        return mPushNotification;
    }

    public ForgottenPassword forgottenPassword() {
        if (mForgottenPassword == null) {
            mForgottenPassword = new ForgottenPassword(this);
            mForgottenPassword.fetch();
        }

        return mForgottenPassword;
    }

    public LootSharedPreferences sharedPreferences() {
        if (mLootSharedPreferences == null) {
            mLootSharedPreferences = new LootSharedPreferences(this);
        }

        return mLootSharedPreferences;
    }

    int getEnvironment() {
        return mApiType;
    }

    LootDatabaseWrapper getDatabase() {
        return mLootDatabase;
    }

    RestClient createRestClient(InterceptorType interceptorType, LootHeader header) {
        if (!mIsInitialized) {
            throw new NotInitializedException();
        }

        return RestClientFactory.createRestClient(mApiType, interceptorType, mContext, header);
    }

    LootHeader createLootHeader() {
        LootHeader header = new LootHeader();

        if (sessions().getUserEmail() != null && sessions().getUserAuthorizationToken() != null && signup().getToken() != null) {
            header.setEmail(sessions().getUserEmail());
            header.setAuthToken(sessions().getUserAuthorizationToken());
            header.setOnboardingToken(signup().getToken());
            header.setAnalyticsDataBase64(mAnalyticsDataBase64);
        }

        return header;
    }

    private String getAnalyticsJson(AnalyticsData analyticsData) {
        Gson gson = new Gson();
        String json = gson.toJson(analyticsData, AnalyticsData.class);

        return (json != null) ? json : "";
    }

    Context getContext() {
        return mContext;
    }

    /**
     * Deprecated since 2.1.0, using LiveData will automatically
     * cancel every action when Fragment is not visible to user.
     * This is no-op operation now.
     */
    @Deprecated
    synchronized void addCall(Call<?> call) {
        // no-op due to LiveData migration
        // should be deleted in next versions
    }

    /**
     * Deprecated since 2.1.0, using LiveData will automatically
     * cancel every action when Fragment is not visible to user.
     * This is no-op operation now.
     */
    @Deprecated
    synchronized void deleteCall(Call<?> call) {
        // no-op due to LiveData migration
        // should be deleted in next versions
    }

    public String getApiUri() {
        return mApiUri;
    }

    private void setApiUri(String apiUrl) {
        mApiUri = apiUrl;
    }

    public void dropBase() {
        if (mSessions != null) {
            mSessions.clearCached();
        }

        if (mUser != null) {
            mUser.clearCached();
            mUser = null;
        }

        if (mCards != null) {
            mCards.clearCached();
        }

        if (mSignUp != null) {
            mSignUp.clearOnBoardingData();
            mSignUp.clearCached();
        }

        if (mServerInfo != null) {
            mServerInfo.clearCached();
        }

        AppExecutors.get().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (mLootDatabase != null) {
                    mLootDatabase.clearAllTables();
                }
            }
        });

        KeyStoreUtil.getInstance(mContext).saveLoginToken("");
        KeyStoreUtil.getInstance(mContext).saveOnBoardingToken("");

        if (mDBHelper == null || mDBHelper.getContext() == null || mDBHelper.getContext().getDatabasePath(DBHelper.DB_NAME) == null || !mDBHelper.getContext().getDatabasePath(DBHelper.DB_NAME).exists()) {
            return;
        }
        mDBHelper.deleteAllDBObjects();
        mDBHelper.clearDBandPass(DBHelper.DB_NAME);

        fetchData();
    }

    private class DatabaseCreatorAsyncTask extends AsyncTask<Void, Void, Boolean> {

        public DatabaseCreationListener listener;

        DatabaseCreatorAsyncTask(DatabaseCreationListener listener) {
            this.listener = listener;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                LootDatabase database = Room.databaseBuilder(mContext, LootDatabase.class, "loot_sdk_db").fallbackToDestructiveMigration()
                        .addMigrations(DatabaseMigrations.MIGRATION_31_32)
                        .addMigrations(DatabaseMigrations.MIGRATION_32_33)
                        .addMigrations(DatabaseMigrations.MIGRATION_33_34)
                        .build();
                mLootDatabase = new LootDatabaseWrapper(database);
                sessions().fetch();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            listener.onDatabaseCreationResultReceived(isSuccess);
        }
    }

    public boolean isInitialized() {
        return mIsInitialized;
    }
}
