package io.loot.lootsdk;


import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;

import io.loot.lootsdk.models.Resource;
import io.loot.lootsdk.models.data.transactions.Transaction;
import io.loot.lootsdk.models.networking.transactions.TransactionListResponse;
import io.loot.lootsdk.networking.ApiResponse;
import io.loot.lootsdk.networking.LootApiInterface;

public class TransactionsDataSource extends PageKeyedDataSource<Integer, Transaction> {

    public final MutableLiveData resourceStatusLiveData;
    private LootApiInterface mApiInterface;
    private LootSDK mLootSDK;
    private String mFrom;
    private String mTo;
    private int mPage;
    private int mLimit;
    private Transaction mPreviousTransaction;
    private boolean hasNextPage;

    public TransactionsDataSource(LootApiInterface mApiInterface, LootSDK mLootSDK, String mFrom, String mTo, int mPage, int mLimit) {
        this.mApiInterface = mApiInterface;
        this.mLootSDK = mLootSDK;
        this.mFrom = mFrom;
        this.mTo = mTo;
        this.mPage = mPage;
        this.mLimit = mLimit;
        this.resourceStatusLiveData = new MutableLiveData<ResourceStatus>();
        this.hasNextPage = true;
    }

    @Override
    public void loadInitial(LoadInitialParams<Integer> params, LoadInitialCallback<Integer, Transaction> callback) {
        setupInitialValuesIfNeeded();
        ApiResponse<TransactionListResponse> response = getResponse();
        if (response == null) {
            return;
        }
        hasNextPage = response.getBody().hasNextPage();
        callback.onResult(getSortedAndSectionedTransactions(response), 0, mPage - 1);
    }

    @Override
    public void loadAfter(LoadParams<Integer> params, LoadCallback<Integer, Transaction> callback) {
        if (!hasNextPage) {
            return;
        }
        setupInitialValuesIfNeeded();
        ApiResponse<TransactionListResponse> response = getResponse();
        if (response == null) {
            return;
        }
        hasNextPage = response.getBody().hasNextPage();
        mPage++;
        callback.onResult(getSortedAndSectionedTransactions(response), mPage - 1);
    }

    @Override
    public void loadBefore(LoadParams<Integer> params, LoadCallback<Integer, Transaction> callback) {

    }

    private ApiResponse<TransactionListResponse> getResponse() {
        ApiResponse<TransactionListResponse> response = null;
        ResourceStatus resourceStatus = new ResourceStatus();
        resourceStatus.status = Resource.Status.LOADING;
        resourceStatusLiveData.postValue(resourceStatus);

        try {
            response = new ApiResponse<TransactionListResponse>(mApiInterface.getTransactionsOnPagePag(mLootSDK.user().getGPSAccount().getId(), mFrom, mTo, mPage, mLimit).execute());

        } catch (Exception e) {
            response = new ApiResponse<TransactionListResponse>(e);
        }

        if (response == null) {
            return null;
        }
        resourceStatus.code = response.getCode();
        if (!response.isSuccessful() || response.getBody() == null) {
            resourceStatus.status = Resource.Status.ERROR;
            resourceStatus.message = response.getErrorMessage();
            resourceStatusLiveData.postValue(resourceStatus);
            return null;
        }
        resourceStatus.status = Resource.Status.SUCCESS;
        resourceStatusLiveData.postValue(resourceStatus);
        return response;
    }

    private void setupInitialValuesIfNeeded() {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd-MM-yyyy");
        if (mFrom == null || mFrom.isEmpty()) {
            mFrom = dtf.print(new DateTime().dayOfMonth().withMinimumValue());
        }
        if (mTo == null || mFrom.isEmpty()) {
            mTo = dtf.print(new DateTime().dayOfMonth().withMaximumValue());
        }
        if (mPage < 0) {
            mPage = 0;
        }
        if (mLimit < 1) {
            mLimit = 30;
        }
    }

    private ArrayList<Transaction> getSortedAndSectionedTransactions(ApiResponse<TransactionListResponse> response) {
        return sectionTransactions(filterAndSortTransactions(TransactionListResponse.parseToDataObject(response.getBody())));

    }

    private ArrayList<Transaction> filterAndSortTransactions(ArrayList<Transaction> transactions) {
        ArrayList<Transaction> settledTransactions = new ArrayList<Transaction>();
        for (Transaction transaction : transactions) {
            if (transaction.getTransactionStatus() != null && (transaction.getTransactionStatus().equals("SETTLED")
                    || transaction.getTransactionStatus().equals("PENDING"))) {
                if ((transaction.getSettlementDate() != null && !transaction.getSettlementDate().isEmpty()) || (transaction.getLocalDate() != null && !transaction.getLocalDate().isEmpty())) {
                    settledTransactions.add(transaction);
                }
            }
        }

        // Sorting by date
        for (Transaction transaction : settledTransactions) {
            DateTime date = new DateTime();
            if (transaction.getSettlementDate() != null && !transaction.getSettlementDate().isEmpty()) {
                try {
                    date = new DateTime(transaction.getSettlementDate());
                } catch (Exception e) {
                }
            } else if (transaction.getLocalDate() != null && !transaction.getLocalDate().isEmpty()) {
                try {
                    date = new DateTime(transaction.getLocalDate());
                } catch (Exception e) {
                }
            }
            transaction.setFormatedDate(date);
            transaction.setDateText(date.toString("EEEE, dd MMMM YYYY"));
        }

        Collections.sort(settledTransactions);
        return settledTransactions;
    }

    private ArrayList<Transaction> sectionTransactions(ArrayList<Transaction> transactions) {
        ArrayList<Transaction> transactionsWithSections = new ArrayList<>();
        if (transactions == null || transactions.isEmpty()) {
            return transactionsWithSections;
        }
        if (mPreviousTransaction == null || !transactions.get(0).getDateText().equals(mPreviousTransaction.getDateText())) {
            mPreviousTransaction = transactions.get(0);
            Transaction sectionedTransaction = new Transaction();
            sectionedTransaction.setId("SECTION+" + mPreviousTransaction.getDateText());
            sectionedTransaction.setDateText(mPreviousTransaction.getDateText());
            transactionsWithSections.add(sectionedTransaction);
        }


        for (Transaction transaction : transactions) {
            if (transaction.getDateText() != null && !transaction.getDateText().equals(mPreviousTransaction.getDateText())) {
                mPreviousTransaction = new Transaction();
                mPreviousTransaction.setId("SECTION+" + transaction.getDateText());
                mPreviousTransaction.setDateText(transaction.getDateText());
                transactionsWithSections.add(mPreviousTransaction);
            }

            transactionsWithSections.add(transaction);
        }
        return transactionsWithSections;
    }

    public class ResourceStatus {
        Resource.Status status;
        String message;
        int code;
    }
}