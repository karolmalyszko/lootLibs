package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import io.loot.lootsdk.models.orm.AccountEntity;
import io.loot.lootsdk.models.orm.ForgottenPasswordEntity;

@Dao
public interface ForgottenPasswordDao extends BaseSynchronousDao<ForgottenPasswordEntity> {

    @Query("SELECT * FROM ForgottenPasswordEntity LIMIT 1")
    LiveData<ForgottenPasswordEntity> load();

    @Query("DELETE FROM ForgottenPasswordEntity")
    void deleteAll();
}
