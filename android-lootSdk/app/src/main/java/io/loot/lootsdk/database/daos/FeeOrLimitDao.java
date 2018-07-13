package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import io.loot.lootsdk.models.orm.AccountEntity;
import io.loot.lootsdk.models.orm.FeeOrLimitEntity;

@Dao
public interface FeeOrLimitDao extends BaseSynchronousDao<FeeOrLimitEntity> {

    @Query("SELECT * FROM FeeOrLimit")
    LiveData<List<FeeOrLimitEntity>> loadAll();

    @Query("DELETE FROM FeeOrLimit")
    void deleteAll();
}
