package io.loot.lootsdk.models.networking.transactions;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.loot.lootsdk.models.data.transactions.Transaction;
import io.loot.lootsdk.models.orm.TransactionEntity;
import lombok.Data;

public @Data class TransactionListResponse {

    @SerializedName("transactions")
    private ArrayList<TransactionResponse> transactions;

    @SerializedName("next_page")
    private String nextPage;

    public boolean hasNextPage() {
        return (nextPage != null && !nextPage.isEmpty());
    }

    public static ArrayList<Transaction> parseToDataObject(TransactionListResponse transactionListResponse) {
        if (transactionListResponse == null) {
            transactionListResponse = new TransactionListResponse();
        }

        if (transactionListResponse.getTransactions() == null) {
            transactionListResponse.setTransactions(new ArrayList<TransactionResponse>());
        }

        ArrayList<Transaction> transactionArrayList = new ArrayList<Transaction>();
        for (TransactionResponse transactionResponse : transactionListResponse.getTransactions()) {
            transactionArrayList.add(TransactionResponse.parseToDataObject(transactionResponse));
        }
        return transactionArrayList;
    }

    public static ArrayList<TransactionEntity> parseToEntityObject(TransactionListResponse transactionListResponse) {
        if (transactionListResponse == null) {
            transactionListResponse = new TransactionListResponse();
        }

        if (transactionListResponse.getTransactions() == null) {
            transactionListResponse.setTransactions(new ArrayList<TransactionResponse>());
        }

        ArrayList<TransactionEntity> transactionEntityArrayList = new ArrayList<TransactionEntity>();
        for (TransactionResponse transactionResponse : transactionListResponse.getTransactions()) {
            transactionEntityArrayList.add(TransactionResponse.parseToEntityObject(transactionResponse));
        }
        return transactionEntityArrayList;
    }
}
