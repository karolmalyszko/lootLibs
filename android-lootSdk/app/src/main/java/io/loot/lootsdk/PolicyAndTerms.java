package io.loot.lootsdk;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import io.loot.lootsdk.exceptions.NullPointerListenerException;
import io.loot.lootsdk.listeners.user.GetTermsAndCoditionsListener;
import io.loot.lootsdk.models.Resource;
import io.loot.lootsdk.models.networking.policyAndTerms.TermsAndConditions;
import io.loot.lootsdk.networking.ApiResponse;
import io.loot.lootsdk.networking.LootApiInterface;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import io.loot.lootsdk.utils.AppExecutors;

public class PolicyAndTerms {

    private LootSDK mLootSDK;
    PolicyAndTerms(LootSDK lootSDK) {
        mLootSDK = lootSDK;
    }

    public LiveData<Resource<TermsAndConditions>> getTermsAndConditions() {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.NO_TOKEN, header).getApiService();

        return new NetworkResource<TermsAndConditions, TermsAndConditions>(AppExecutors.get()) {
            @Override
            protected TermsAndConditions proceedData(@NonNull TermsAndConditions item) {
                return item;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<TermsAndConditions>> createCall() {
                return apiInterface.getTermsAndConditions();
            }
        }.asLiveData();
    }

}
