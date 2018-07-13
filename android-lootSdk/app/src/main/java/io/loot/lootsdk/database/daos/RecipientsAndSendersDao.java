package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.loot.lootsdk.models.orm.ContactTransactionEntity;
import io.loot.lootsdk.models.orm.RecipientsAndSendersEntity;

@Dao
public interface RecipientsAndSendersDao extends BaseSynchronousDao<RecipientsAndSendersEntity>{
    @Query("SELECT * FROM RecipientsAndSenders WHERE id = :paymentId")
    LiveData<List<RecipientsAndSendersEntity>> loadById(String paymentId);

    @Query("DELETE FROM RecipientsAndSenders")
    void deleteAll();

    @Query("DELETE FROM RecipientsAndSenders WHERE id = :paymentId")
    void deleteById(String paymentId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<RecipientsAndSendersEntity> entity);
}
