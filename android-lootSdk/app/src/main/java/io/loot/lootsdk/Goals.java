package io.loot.lootsdk;


import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.loot.lootsdk.database.daos.SavingGoalDao;
import io.loot.lootsdk.exceptions.NullPointerListenerException;
import io.loot.lootsdk.listeners.user.CreateUpdateGoalListener;
import io.loot.lootsdk.listeners.user.GetGoalsListener;
import io.loot.lootsdk.listeners.user.LoadMoneyListener;
import io.loot.lootsdk.listeners.user.UnloadMoneyListener;
import io.loot.lootsdk.models.ActionResult;
import io.loot.lootsdk.models.Resource;
import io.loot.lootsdk.models.data.SavingGoal;
import io.loot.lootsdk.models.networking.savingGoals.CreateSavingsGoalRequest;
import io.loot.lootsdk.models.networking.savingGoals.GoalTransferMoneyRequest;
import io.loot.lootsdk.models.networking.savingGoals.SavingsGoalResponse;
import io.loot.lootsdk.models.networking.savingGoals.SavingsGoalsListResponse;
import io.loot.lootsdk.models.networking.savingGoals.UpdateSavingsGoalRequest;
import io.loot.lootsdk.models.orm.SavingsGoalEntity;
import io.loot.lootsdk.networking.ApiResponse;
import io.loot.lootsdk.networking.LootApiInterface;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import io.loot.lootsdk.utils.AppExecutors;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// TODO 09.01.2018
// This API have a bit different behavior in terms of Action Results.
// Here, we don't use it because of our Fragment and Provider bad architecture
// and we have to return LiveData<Resource<*>> to be sure everything
// will work as expected.
//
// After integration with AAC in LootApp we can reorganize it
// and make all 'actions' (like update, create and delete)
// return LiveData<ActionResult> instead of LiveData<Resource<*>>
// to align it to other APIs.

class Goals {

    private LootSDK mLootSDK;
    private SavingGoalDao mSavingGoalDao;

    Goals(LootSDK lootSDK) {
        mLootSDK = lootSDK;
        mSavingGoalDao = lootSDK.getDatabase().savingGoalDao();
    }

    public LiveData<SavingGoal> getGoalById(String goalId) {
        return Transformations.map(mSavingGoalDao.loadById(goalId), new Function<SavingsGoalEntity, SavingGoal>() {
            @Override
            public SavingGoal apply(SavingsGoalEntity input) {
                if (input == null) {
                    return new SavingGoal();
                }

                return input.parseToDataObject();
            }
        });
    }

    public LiveData<Resource<List<SavingGoal>>> getGoals(final String userId) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkBoundResource<List<SavingGoal>, SavingsGoalsListResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull SavingsGoalsListResponse item) {
                mSavingGoalDao.deleteAll();
                
                for (SavingsGoalResponse response : item.getGoals()) {
                    mSavingGoalDao.insert(SavingsGoalResponse.parseToEntityObject(response));
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<SavingGoal> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<SavingGoal>> loadFromDb() {
                return Transformations.map(mSavingGoalDao.loadAll(), new Function<List<SavingsGoalEntity>, List<SavingGoal>>() {
                    @Override
                    public List<SavingGoal> apply(List<SavingsGoalEntity> input) {
                        if (input == null) {
                            return null;
                        }

                        List<SavingGoal> savingGoals = new ArrayList<>();
                        for (SavingsGoalEntity entity : input) {
                            if (entity == null) {
                                continue;
                            }

                            savingGoals.add(entity.parseToDataObject());
                        }

                        return savingGoals;
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SavingsGoalsListResponse>> createCall() {
                return apiInterface.getAllGoals(userId);
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> createGoal(final String userId, final CreateSavingsGoalRequest request) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkBoundAction<SavingsGoalResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull SavingsGoalResponse item) {
                mSavingGoalDao.insert(SavingsGoalResponse.parseToEntityObject(item));
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SavingsGoalResponse>> createCall() {
                return apiInterface.createSavingsGoal(userId, request);
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> updateGoal(final String userId, final String goalId, final UpdateSavingsGoalRequest request) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkBoundAction<SavingsGoalResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull SavingsGoalResponse item) {
                mSavingGoalDao.insert(SavingsGoalResponse.parseToEntityObject(item));
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SavingsGoalResponse>> createCall() {
                return apiInterface.updateSavingsGoal(userId, goalId, request);
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> deleteGoal(final String userId, final String goalId) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkBoundAction<SavingsGoalResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull SavingsGoalResponse item) {
                mSavingGoalDao.deleteById(item.getId());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SavingsGoalResponse>> createCall() {
                return apiInterface.deleteSavingsGoal(userId, goalId);
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> loadMoney(final String userId, final String goalId, final int amount) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkBoundAction<SavingsGoalResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull SavingsGoalResponse item) {
                mSavingGoalDao.insert(SavingsGoalResponse.parseToEntityObject(item));
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SavingsGoalResponse>> createCall() {
                GoalTransferMoneyRequest request = new GoalTransferMoneyRequest(amount);
                return apiInterface.goalLoadMoney(userId, goalId, request);
            }
        }.asLiveData();
    }

    public LiveData<Resource<List<SavingGoal>>> fetchGoals(final String userId) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkResource<List<SavingGoal>, SavingsGoalsListResponse>(AppExecutors.get()) {
            @Override
            protected List<SavingGoal> proceedData(@NonNull SavingsGoalsListResponse item) {
                mSavingGoalDao.deleteAll();

                for (SavingsGoalResponse goalResponse : item.getGoals()) {
                    mSavingGoalDao.insert(SavingsGoalResponse.parseToEntityObject(goalResponse));
                }

                return SavingsGoalsListResponse.parseToDataObject(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SavingsGoalsListResponse>> createCall() {
                return apiInterface.getAllGoals(userId);
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> unloadMoneyFromGoal(final String userId, final String goaldId, final int amount) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkBoundAction<SavingsGoalResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull SavingsGoalResponse item) {
                mSavingGoalDao.insert(SavingsGoalResponse.parseToEntityObject(item));
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SavingsGoalResponse>> createCall() {
                return apiInterface.goalUnloadMoney(userId, goaldId, new GoalTransferMoneyRequest(amount));
            }
        }.asLiveData();
    }

    @Deprecated
    public LiveData<Resource<SavingGoal>> unloadMoney(final String userId, final String goaldId, final int amount) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkResource<SavingGoal, SavingsGoalResponse>(AppExecutors.get()) {
            @Override
            protected SavingGoal proceedData(@NonNull SavingsGoalResponse item) {
                return SavingsGoalResponse.parseToDataObject(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SavingsGoalResponse>> createCall() {
                return apiInterface.goalUnloadMoney(userId, goaldId, new GoalTransferMoneyRequest(amount));
            }
        }.asLiveData();
    }
}