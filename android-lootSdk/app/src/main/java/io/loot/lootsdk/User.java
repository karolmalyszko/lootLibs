package io.loot.lootsdk;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import io.loot.lootsdk.database.daos.AccountDao;
import io.loot.lootsdk.database.daos.BudgetDao;
import io.loot.lootsdk.database.daos.TopUpLimitsDao;
import io.loot.lootsdk.database.daos.UserInfoDao;
import io.loot.lootsdk.exceptions.IncorrectPinException;
import io.loot.lootsdk.exceptions.NullPointerListenerException;
import io.loot.lootsdk.listeners.user.AccountCloseListener;
import io.loot.lootsdk.models.ActionResult;
import io.loot.lootsdk.models.Resource;
import io.loot.lootsdk.models.data.Budget;
import io.loot.lootsdk.models.data.SavingGoal;
import io.loot.lootsdk.models.data.category.Category;
import io.loot.lootsdk.models.data.transactions.Spending;
import io.loot.lootsdk.models.data.transactions.Transaction;
import io.loot.lootsdk.models.data.transfer.Transfer;
import io.loot.lootsdk.models.data.userinfo.Account;
import io.loot.lootsdk.models.data.userinfo.AccountDetails;
import io.loot.lootsdk.models.data.userinfo.Address;
import io.loot.lootsdk.models.data.userinfo.ContactPreferences;
import io.loot.lootsdk.models.data.userinfo.PersonalDetails;
import io.loot.lootsdk.models.data.userinfo.UpdateUserDetailsRequest;
import io.loot.lootsdk.models.data.userinfo.UserInfo;
import io.loot.lootsdk.models.networking.savingGoals.CreateSavingsGoalRequest;
import io.loot.lootsdk.models.networking.savingGoals.UpdateSavingsGoalRequest;
import io.loot.lootsdk.models.networking.topUp.TopUpLimitsResponse;
import io.loot.lootsdk.models.networking.transfer.MakeTransferRequest;
import io.loot.lootsdk.models.networking.user.BudgetResponse;
import io.loot.lootsdk.models.networking.user.ChangePasswordRequest;
import io.loot.lootsdk.models.networking.user.ContactPreferencesRequest;
import io.loot.lootsdk.models.networking.user.ContactPreferencesResponse;
import io.loot.lootsdk.models.networking.user.SetBudgetRequest;
import io.loot.lootsdk.models.networking.user.UploadProfileImageRequest;
import io.loot.lootsdk.models.networking.user.userinfo.AccountDetailsResponse;
import io.loot.lootsdk.models.networking.user.userinfo.AccountResponse;
import io.loot.lootsdk.models.networking.user.userinfo.AddressResponse;
import io.loot.lootsdk.models.networking.user.userinfo.PersonalDetailsResponse;
import io.loot.lootsdk.models.networking.user.userinfo.UserDataUpdateRequest;
import io.loot.lootsdk.models.networking.user.userinfo.UserInfoResponse;
import io.loot.lootsdk.models.networking.user.userinfo.UserResponse;
import io.loot.lootsdk.models.orm.AccountEntity;
import io.loot.lootsdk.models.orm.BudgetEntity;
import io.loot.lootsdk.models.orm.UserInfoEntity;
import io.loot.lootsdk.networking.ApiResponse;
import io.loot.lootsdk.networking.LootApiInterface;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import io.loot.lootsdk.utils.AppExecutors;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static io.loot.lootsdk.models.networking.ErrorExtractor.NOT_FOUND;

public class User implements Observer<List<AccountEntity>> {

    private LootSDK mLootSDK;

    private Transactions userTransactions;
    private Goals userGoals;

    private UserInfoDao mUserInfoDao;
    private AccountDao mAccountDao;
    private BudgetDao mBudgetDao;
    private TopUpLimitsDao mTopUpLimitsDao;

    private AccountDetails mCachedAccountDetails;
    private PersonalDetails mCachedPersonalDetails;
    private Address mCachedAddress;

    User(LootSDK lootSDK) {
        mLootSDK = lootSDK;
        userGoals = new Goals(mLootSDK);
        userTransactions = new Transactions(mLootSDK);

        mUserInfoDao = mLootSDK.getDatabase().userInfoDao();
        mAccountDao = mLootSDK.getDatabase().accountDao();
        mBudgetDao = mLootSDK.getDatabase().budgetDao();
        mTopUpLimitsDao = mLootSDK.getDatabase().topUpLimitsDao();
    }


    public LiveData<Resource<ContactPreferences>> getContactPreferences() {
        return new NetworkResource<ContactPreferences, ContactPreferencesResponse>(AppExecutors.get()) {
            @Override
            protected ContactPreferences proceedData(@NonNull ContactPreferencesResponse item) {
                return ContactPreferencesResponse.parseToDataObject(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ContactPreferencesResponse>> createCall() {
                return getSessionTokenService().getContactPreferences();
            }
        }.asLiveData();
    }

    public LiveData<Resource<ContactPreferences>> setContactPreferences(final ContactPreferences contactPreferences) {
        return new NetworkResource<ContactPreferences, ContactPreferencesResponse>(AppExecutors.get()) {
            @Override
            protected ContactPreferences proceedData(@NonNull ContactPreferencesResponse item) {
                return ContactPreferencesResponse.parseToDataObject(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ContactPreferencesResponse>> createCall() {
                ContactPreferencesRequest contactPreferencesRequest = new ContactPreferencesRequest(contactPreferences);
                return getSessionTokenService().setContactPreferences(contactPreferencesRequest);
            }
        }.asLiveData();
    }

    void fetch() {
        LiveData<List<AccountEntity>> liveData = mAccountDao.loadAll();
        liveData.observeForever(this);
    }

    private LootApiInterface getSessionTokenService() {
        LootHeader header = mLootSDK.createLootHeader();
        return mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();
    }

    public void fetchBudget() {
        getSessionTokenService().fetchBudget().enqueue(new Callback<BudgetResponse>() {
            @Override
            public void onResponse(@NonNull Call<BudgetResponse> call, @NonNull Response<BudgetResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mBudgetDao.insert(BudgetResponse.parseToEntityObject(response.body()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<BudgetResponse> call, @NonNull Throwable t) {

            }
        });
    }

    public LiveData<Resource<Budget>> getBudget() {
        return new NetworkBoundResource<Budget, BudgetResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull BudgetResponse item) {
                BudgetEntity entity = BudgetResponse.parseToEntityObject(item);
                mBudgetDao.insert(entity);
            }

            @Override
            protected void onFetchFailed(String erroMessage) {
                if (erroMessage.equals(NOT_FOUND)) {
                    AppExecutors.get().mainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            mBudgetDao.deleteAll();
                        }
                    });
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable Budget data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<Budget> loadFromDb() {
                return Transformations.map(mBudgetDao.loadBudget(), new Function<BudgetEntity, Budget>() {
                    @Override
                    public Budget apply(BudgetEntity input) {
                        if (input == null) {
                            return new Budget();
                        }

                        return input.parseToDataObject();
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<BudgetResponse>> createCall() {
                return getSessionTokenService().getBudget();
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> setBudget(final String amount) {
        return new NetworkBoundAction<BudgetResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull BudgetResponse item) {
                mBudgetDao.insert(BudgetResponse.parseToEntityObject(item));
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<BudgetResponse>> createCall() {
                return getSessionTokenService().setBudget(new SetBudgetRequest(amount));
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> deleteBudget() {
        return new NetworkBoundAction<ResponseBody>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ResponseBody item) {
                mBudgetDao.deleteAll();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return getSessionTokenService().deleteBudget();
            }
        }.asLiveData();
    }

    public LiveData<Resource<AccountDetails>> getAccountDetails() {
        return new NetworkBoundResource<AccountDetails, AccountDetailsResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull AccountDetailsResponse item) {
                mAccountDao.deleteAll();
                mCachedAccountDetails = AccountDetailsResponse.parseToDataObject(item);

                for (AccountResponse account : item.getAccounts()) {
                    mAccountDao.insert(AccountResponse.parseToEntityObject(account));
                }

                mTopUpLimitsDao.insert(TopUpLimitsResponse.parseToEntityObject(item.getTopUpLimits()));
            }

            @Override
            protected boolean shouldFetch(@Nullable AccountDetails data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<AccountDetails> loadFromDb() {
                return Transformations.map(mAccountDao.loadAll(), new Function<List<AccountEntity>, AccountDetails>() {
                    @Override
                    public AccountDetails apply(List<AccountEntity> input) {
                        ArrayList<Account> accounts = new ArrayList<>();

                        for (AccountEntity entity : input) {
                            accounts.add(entity.parseToDataObject());
                        }

                        return new AccountDetails(accounts);
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<AccountDetailsResponse>> createCall() {
                return getSessionTokenService().getAccountDetails();
            }
        }.asLiveData();
    }

    public LiveData<UserInfo> getCachedUserDetails() {
        return Transformations.map(mUserInfoDao.load(), new Function<UserInfoEntity, UserInfo>() {
            @Override
            public UserInfo apply(UserInfoEntity input) {
                if (input == null) {
                    return null;
                }

                return input.parseToDataObject();
            }
        });
    }

    public LiveData<Resource<UserInfo>> getUserDetails() {
        return new NetworkBoundResource<UserInfo, UserResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull UserResponse item) {
                mUserInfoDao.insert(UserInfoResponse.parseToEntityObject(item.getUserInfo()));
            }

            @Override
            protected boolean shouldFetch(@Nullable UserInfo data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<UserInfo> loadFromDb() {
                return Transformations.map(mUserInfoDao.load(), new Function<UserInfoEntity, UserInfo>() {
                    @Override
                    public UserInfo apply(UserInfoEntity input) {
                        if (input == null) {
                            return null;
                        }

                        return input.parseToDataObject();
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<UserResponse>> createCall() {
                return getSessionTokenService().getPersonalDetails();
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> updateUserDetails(UpdateUserDetailsRequest request) {
        final UserDataUpdateRequest dataUpdateRequest = new UserDataUpdateRequest();

        PersonalDetailsResponse personalDetails = new PersonalDetailsResponse();
        personalDetails.setPreferredName(request.getPreferredName());
        personalDetails.setTitleCode(request.getTitle());
        personalDetails.setAddress(AddressResponse.parseToResponseObject(request.getAddress()));

        dataUpdateRequest.setPersonalDetails(personalDetails);

        return new NetworkBoundAction<UserResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull UserResponse item) {
                mUserInfoDao.insert(UserInfoResponse.parseToEntityObject(item.getUserInfo()));
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<UserResponse>> createCall() {
                return getSessionTokenService().updateUser(dataUpdateRequest);
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> validateTransfer(Transfer transfer) {
        final MakeTransferRequest request = new MakeTransferRequest(transfer.getRecipentName(), transfer.getRecipentAccountNumber(),
                transfer.getRecipentSortCode(), transfer.getAmount(), transfer.getReference());

        return new NetworkBoundAction<ResponseBody>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ResponseBody item) {
                // nothing to do with this
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return getSessionTokenService().validateTransfer(getGPSAccount().getId(), request);
            }
        }.asLiveData();
    }

    private LiveData<ActionResult> makeTransfer(final MakeTransferRequest request) {
        return new NetworkBoundAction<ResponseBody>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ResponseBody item) {

            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return getSessionTokenService().makeTransfer(getGPSAccount().getId(), request);
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> makeTransfer(Transfer transfer) {
        MakeTransferRequest request = new MakeTransferRequest(transfer.getRecipentName(), transfer.getRecipentAccountNumber(),
                transfer.getRecipentSortCode(), transfer.getAmount(), transfer.getReference());


        return makeTransfer(request);
    }

    public LiveData<ActionResult> makeTransferWithPassword(final Transfer transfer, String password) {
       final MediatorLiveData<ActionResult> resultLiveData = new MediatorLiveData<>();
       final LiveData<ActionResult> loginResult = mLootSDK.sessions().login(mLootSDK.sessions().getUserEmail(), password);

       resultLiveData.addSource(loginResult, new Observer<ActionResult>() {
           @Override
           public void onChanged(@Nullable ActionResult result) {
               if (result == null) {
                   return;
               }

               if (result.isSuccess()) {
                   resultLiveData.removeSource(loginResult);
                   resultLiveData.addSource(makeTransfer(transfer), new Observer<ActionResult>() {
                       @Override
                       public void onChanged(@Nullable ActionResult result) {
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

    public LiveData<ActionResult> makeTransferWithPin(final Transfer transfer, String pin) throws IncorrectPinException {
        final MediatorLiveData<ActionResult> resultLiveData = new MediatorLiveData<>();
        final LiveData<ActionResult> loginResult = mLootSDK.sessions().loginByPIN(pin);

        resultLiveData.addSource(loginResult, new Observer<ActionResult>() {
            @Override
            public void onChanged(@Nullable ActionResult actionResult) {
                if (actionResult == null) {
                    return;
                }

                if (actionResult.isSuccess()) {
                    resultLiveData.removeSource(loginResult);
                    resultLiveData.addSource(makeTransfer(transfer), new Observer<ActionResult>() {
                        @Override
                        public void onChanged(@Nullable ActionResult actionResult) {
                            resultLiveData.setValue(actionResult);
                        }
                    });
                } else {
                    resultLiveData.setValue(actionResult);
                }
            }
        });

        return resultLiveData;
    }

    public LiveData<ActionResult> fetchAccountDetails() {
        return new NetworkBoundAction<AccountDetailsResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull AccountDetailsResponse item) {
                for (AccountResponse account : item.getAccounts()) {
                    mAccountDao.insert(AccountResponse.parseToEntityObject(account));
                }
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<AccountDetailsResponse>> createCall() {
                return getSessionTokenService().getAccountDetails();
            }
        }.asLiveData();
    }

    public AccountDetails getCachedAccountDetails() {
        return mCachedAccountDetails != null ? new AccountDetails(mCachedAccountDetails) : new AccountDetails();
    }

    public PersonalDetails getCachedPersonalDetails() {
        return mCachedPersonalDetails != null ? new PersonalDetails(mCachedPersonalDetails) : new PersonalDetails();
    }

    public Address getCachedAddress() {
        return mCachedAddress != null ? new Address(mCachedAddress) : new Address();
    }

    public Account getGPSAccount() {
        fetch();

        if (mLootSDK.getEnvironment() == LootSDK.API_TYPE_STAGING || mLootSDK.getEnvironment() == LootSDK.API_TYPE_PRE_PROD) {
            for (Account account : getCachedAccountDetails().getAccounts()) {
                if (account.getProvider().getName().toUpperCase().equals("DUMMY")) {
                    return account;
                }
            }
        }

        for (Account account : getCachedAccountDetails().getAccounts()) {
            if (account.getProvider().getName().toUpperCase().equals("GPS")) {
                return account;
            }
        }

        return new Account();
    }

    public LiveData<ActionResult> updateProfilePhoto(final String base64PhotoImage) {
        return new NetworkBoundAction<UserResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull UserResponse item) {
                mUserInfoDao.insert(UserInfoResponse.parseToEntityObject(item.getUserInfo()));
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<UserResponse>> createCall() {
                return getSessionTokenService().uploadProfileImage(new UploadProfileImageRequest(base64PhotoImage));
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> changePassword(final String newPassword, final String oldPassword) {
        return new NetworkBoundAction<ResponseBody>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ResponseBody item) {
                // nothing to do with it
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
                changePasswordRequest.setNewPassword(newPassword);
                changePasswordRequest.setPassword(oldPassword);

                return getSessionTokenService().changePassword(changePasswordRequest);
            }
        }.asLiveData();
    }

    public boolean isAccountActive() {
        return getGPSAccount().getStatus().toLowerCase().equals("open");
    }

    public LiveData<SavingGoal> getGoalById(String goalId) {
        return userGoals.getGoalById(goalId);
    }

    public LiveData<Resource<List<SavingGoal>>> getGoals() {
        String userId = getGPSAccount().getId();
        return userGoals.getGoals(userId);
    }

    public LiveData<ActionResult> createGoal(CreateSavingsGoalRequest request) {
        String userId = getGPSAccount().getId();
        return userGoals.createGoal(userId, request);
    }

    public LiveData<ActionResult> updateGoal(String goalId, UpdateSavingsGoalRequest updateSavingsGoalRequest) {
        String userId = getGPSAccount().getId();
        return userGoals.updateGoal(userId, goalId, updateSavingsGoalRequest);
    }

    public LiveData<ActionResult> deleteGoal(String goalId) {
        String userId = getGPSAccount().getId();
        return userGoals.deleteGoal(userId, goalId);
    }

    public LiveData<ActionResult> loadMoneyToGoal(SavingGoal goal, int amount) {
        String userId = getGPSAccount().getId();
        return userGoals.loadMoney(userId, goal.getId(), amount);
    }

    @Deprecated
    public LiveData<Resource<SavingGoal>> unloadMoneyFromGoal(String goalId, int amount) {
        String userId = getGPSAccount().getId();
        return userGoals.unloadMoney(userId, goalId, amount);
    }

    public LiveData<Resource<List<SavingGoal>>> fetchGoals() {
        String userId = getGPSAccount().getId();
        return userGoals.fetchGoals(userId);
    }

    public LiveData<ActionResult> unloadMoneyFromGoal(SavingGoal goal, int amount) {
        String userId = getGPSAccount().getId();
        return userGoals.unloadMoneyFromGoal(userId, goal.getId(), amount);
    }

    public LiveData<Resource<Transactions.PaginatedTransactionsHolder>> getTransactionsOnPage(String from, String to, int page, int limit) {
        String userId = getGPSAccount().getId();
        return userTransactions.getTransactionsOnPage(userId, from, to, page, limit);
    }

    public LiveData<Resource<PagedList<Transaction>>> getTransactionsOnPagePaginated(String from, String to, int page, int limit) {
        String userId = getGPSAccount().getId();
        return userTransactions.getTransactionsPaginated(userId, from, to, page, limit);
    }

    public LiveData<Resource<List<Transaction>>> getAllTransactions() {
        String userId = getGPSAccount().getId();
        return userTransactions.getAllTransactions(userId);
    }

    public LiveData<Resource<List<Transaction>>> getTransactionsToConfirm() {
        return userTransactions.getTransactionsToConfirm();
    }

    public LiveData<ActionResult> changeCategory(Transaction transaction, Category category) {
        return userTransactions.changeCategory(transaction, category);
    }

    public LiveData<Resource<Transaction>> setTransactionStatus(String transactionId, String status) {
        String userId = getGPSAccount().getId();
        return userTransactions.setTransactionStatus(userId, transactionId, status);
    }

    public LiveData<Resource<ResponseBody>> getTransactionsStatement(boolean getHTMLPreview, String from, String to) {
        String userId = getGPSAccount().getId();
        return userTransactions.getTransactionsStatement(userId, getHTMLPreview, from, to);
    }

    public LiveData<Resource<Spending>> getCategorySpendings(String categoryId, String date) {
        return userTransactions.getSpendingByCategory(categoryId, date);
    }

    public LiveData<Resource<Spending>> getMerchantSpendings(String merchantId, String date) {
        return userTransactions.getSpendingsByMerchant(merchantId, date);
    }

    @Deprecated
    public void closeAccount(final AccountCloseListener listener) {
        if (listener == null) {
            throw new NullPointerListenerException();
        } else {
            throw new RuntimeException("YOU CANNOT CLOSE YOUR ACCOUNT RIGHT NOW");
        }

//        //COMMENTED ON PURPOSE
//        LootHeader header = mLootSDK.createLootHeader();
//        LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();
//
//        Call<ResponseBody> call = apiInterface.closeAccount();
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful()) {
//                    listener.onAccountCloseSuccess();
//                    return;
//                }
//
//                try {
//                    String stringResponse = response.errorBody().string();
//                    int code = response.code();
//                    if (ErrorExtractor.isSessionExpired(code, stringResponse)) {
//                            listener.onSessionExpired();
//                        }
//                        else if (ErrorExtractor.isUserBlocked(code, stringResponse)) {
//                            listener.onUserBlocked();
//                        }
//                        else {
//                        String errorMessage = ErrorExtractor.extract(code, stringResponse);
//                        listener.onAccountCloseError(errorMessage);
//                    } else {
//                        listener.onSessionExpired();
//                    }
//                } catch (IOException e) {
//                    listener.onAccountCloseError("UNEXPECTED_ERROR");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                listener.onFailure(t.getMessage());
//            }
//        });
    }

    public void clearCached() {
        mCachedAccountDetails = null;
        mCachedAddress = null;
        mCachedPersonalDetails = null;
    }

    @Override
    public void onChanged(@Nullable List<AccountEntity> accountEntities) {
        if (accountEntities == null) {
            return;
        }

        ArrayList<Account> accounts = new ArrayList<>();
        for (AccountEntity entity : accountEntities) {

            // We can safely assume that we can override those variables as even if user
            // has more than one account (in that case, legacy, Contis' one) those
            // data will be the same in both accounts.

            if (entity.getUserInfo() != null && entity.getUserInfo().getPersonalDetails() != null) {
                mCachedPersonalDetails = entity.getUserInfo().getPersonalDetails().parseToDataObject();
                if (entity.getUserInfo().getPersonalDetails().getAddress() != null) {
                    mCachedAddress = entity.getUserInfo().getPersonalDetails().getAddress().parseToDataObject();
                }
            }

            accounts.add(entity.parseToDataObject());
        }

        mCachedAccountDetails = new AccountDetails(accounts);
    }
}
