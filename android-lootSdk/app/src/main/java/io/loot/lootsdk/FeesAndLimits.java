package io.loot.lootsdk;


import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import io.loot.lootsdk.database.daos.FeeOrLimitDao;
import io.loot.lootsdk.exceptions.NullPointerListenerException;
import io.loot.lootsdk.listeners.user.GetFeesAndLimitsListener;
import io.loot.lootsdk.models.Resource;
import io.loot.lootsdk.models.data.userinfo.FeeOrLimit;
import io.loot.lootsdk.models.networking.feesAndLimits.FeeOrLimitResponse;
import io.loot.lootsdk.models.orm.FeeOrLimitEntity;
import io.loot.lootsdk.networking.ApiResponse;
import io.loot.lootsdk.networking.LootApiInterface;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import io.loot.lootsdk.utils.AppExecutors;

public class FeesAndLimits {

    private LootSDK mLootSDK;

    private FeeOrLimitDao mFeeOrLimitDao;

    FeesAndLimits(LootSDK lootSDK) {
        mLootSDK = lootSDK;
        mFeeOrLimitDao = lootSDK.getDatabase().feeOrLimitDao();
    }

    public LiveData<Resource<List<FeeOrLimit>>> getFeesAndLimits() {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkBoundResource<List<FeeOrLimit>, List<FeeOrLimitResponse>>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull List<FeeOrLimitResponse> item) {
                mFeeOrLimitDao.deleteAll();

                for (FeeOrLimitResponse response : item) {
                    mFeeOrLimitDao.insert(FeeOrLimitResponse.parseToEntityObject(response));
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<FeeOrLimit> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<FeeOrLimit>> loadFromDb() {
                return Transformations.map(mFeeOrLimitDao.loadAll(), new Function<List<FeeOrLimitEntity>, List<FeeOrLimit>>() {
                    @Override
                    public List<FeeOrLimit> apply(List<FeeOrLimitEntity> input) {
                        if (input == null) {
                            return null;
                        }

                        List<FeeOrLimit> feeOrLimits = new ArrayList<>();
                        for (FeeOrLimitEntity entity : input) {
                            if (entity == null) {
                                continue;
                            }

                            feeOrLimits.add(entity.parseToDataObject());
                        }

                        return feeOrLimits;
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<FeeOrLimitResponse>>> createCall() {
                return apiInterface.getFeesAndLimits();
            }
        }.asLiveData();
    }
}
