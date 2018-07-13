package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.loot.lootsdk.models.orm.ContactEntity;
import io.loot.lootsdk.models.orm.ContactTransactionEntity;
import io.loot.lootsdk.models.orm.TransactionEntity;

@Dao
public interface ContactTransactionDao extends BaseSynchronousDao<ContactTransactionEntity>{
    @Query("SELECT * FROM ContactTransactions WHERE contactId = :contactId")
    LiveData<List<ContactTransactionEntity>> loadById(String contactId);

    @Query("SELECT * FROM ContactTransactions ct, RecipientsAndSenders rs WHERE ct.paymentDetailsId = rs.id AND rs.accountNumber = :accountNumber AND rs.sortCode = :sortCode")
    LiveData<List<ContactTransactionEntity>> loadByAccountDetails (String accountNumber, String sortCode);


    @Query("DELETE FROM ContactTransactions")
    void deleteAll();

    @Query("DELETE FROM ContactTransactions WHERE contactId = :contactId")
    void deleteById(String contactId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ContactTransactionEntity> entity);
}
