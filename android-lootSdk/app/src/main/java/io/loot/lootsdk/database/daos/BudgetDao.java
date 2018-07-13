package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import io.loot.lootsdk.models.orm.BudgetEntity;

@Dao
public interface BudgetDao extends BaseSynchronousDao<BudgetEntity>{

    @Query("SELECT * FROM Budget LIMIT 1")
    LiveData<BudgetEntity> loadBudget();

    @Query("SELECT * FROM Budget LIMIT 1")
    BudgetEntity loadBudgetSynchronously();

    @Query("DELETE FROM Budget")
    void deleteAll();
}
