package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

import io.loot.lootsdk.models.orm.TransactionEntity;

@Dao
public interface TransactionDao extends BaseSynchronousDao<TransactionEntity> {

    @Query("SELECT * FROM Transactions")
    LiveData<List<TransactionEntity>> loadAll();

    @Query("SELECT * FROM Transactions WHERE persistedLocalDate BETWEEN datetime(:from) AND datetime(:to) ORDER BY datetime(localDate) DESC LIMIT :limit OFFSET (:limit*:page)")
    LiveData<List<TransactionEntity>> loadByDateRange(String from, String to, int page, int limit);

    @Query("SELECT * FROM Transactions WHERE persistedLocalDate BETWEEN :from AND :to ORDER BY datetime(localDate) DESC LIMIT :limit OFFSET (:limit*:page)")
    LiveData<List<TransactionEntity>> loadByDateRange(Date from, Date to, int page, int limit);

    @Query("SELECT * FROM Transactions WHERE persistedLocalDate BETWEEN datetime(:from) AND datetime(:to) ORDER BY datetime(localDate) DESC LIMIT :limit OFFSET (:limit*:page)")
    DataSource.Factory<Integer, TransactionEntity> loadByDateRangePagination(String from, String to, int page, int limit);

    @Query("SELECT * FROM Transactions WHERE persistedLocalDate BETWEEN :from AND :to ORDER BY datetime(localDate) DESC LIMIT :limit OFFSET (:limit*:page)")
    DataSource.Factory<Integer, TransactionEntity> loadByDateRangePagination(Date from, Date to, int page, int limit);

    @Query("DELETE FROM Transactions")
    void deleteAll();
}
