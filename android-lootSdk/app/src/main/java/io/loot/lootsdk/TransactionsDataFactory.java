package io.loot.lootsdk;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import io.loot.lootsdk.models.data.transactions.Transaction;
import io.loot.lootsdk.networking.LootApiInterface;

public class TransactionsDataFactory extends DataSource.Factory<Integer, Transaction> {
    public MutableLiveData<TransactionsDataSource> datasourceLiveData;
    LootApiInterface mApiInterface;
    LootSDK mLootSDK;
    String from;
    String to;
    int page;
    int limit;
    TransactionsDataSource dataSource;

    public TransactionsDataFactory(LootApiInterface mApiInterface, LootSDK mLootSDK, String from, String to, int page, int limit) {
        this.mApiInterface = mApiInterface;
        this.mLootSDK = mLootSDK;
        this.from = from;
        this.to = to;
        this.page = page;
        this.limit = limit;
        this.datasourceLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, Transaction> create() {

        TransactionsDataSource dataSource = new TransactionsDataSource(mApiInterface, mLootSDK, from, to, page, limit);
        datasourceLiveData.postValue(dataSource);
        return dataSource;
    }
}
