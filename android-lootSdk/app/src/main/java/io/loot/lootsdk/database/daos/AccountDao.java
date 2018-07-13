package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.loot.lootsdk.models.orm.AccountEntity;

@Dao
public interface AccountDao extends BaseSynchronousDao<AccountEntity> {

    @Query("SELECT * FROM Account")
    LiveData<List<AccountEntity>> loadAll();

    @Query("SELECT * FROM Account")
    List<AccountEntity> loadAllSynchronously();

    @Query("DELETE FROM Account")
    void deleteAll();
}
