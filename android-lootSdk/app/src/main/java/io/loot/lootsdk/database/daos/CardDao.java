package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import io.loot.lootsdk.models.orm.AccountEntity;
import io.loot.lootsdk.models.orm.CardEntity;

@Dao
public interface CardDao extends BaseSynchronousDao<CardEntity> {

    @Query("SELECT * FROM Card")
    LiveData<List<CardEntity>> loadAll();

    @Query("DELETE FROM Card")
    void deleteAll();
}
