package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.loot.lootsdk.models.orm.SavingsGoalEntity;

@Dao
public interface SavingGoalDao extends BaseSynchronousDao<SavingsGoalEntity>{

    @Query("SELECT * FROM SavingGoal")
    LiveData<List<SavingsGoalEntity>> loadAll();

    @Query("DELETE FROM SavingGoal")
    int deleteAll();

    @Query("DELETE FROM SavingGoal WHERE id = :id")
    void deleteById(String id);

    @Query("SELECT * FROM SavingGoal WHERE id = :id")
    LiveData<SavingsGoalEntity> loadById(String id);

    @Update
    void update(SavingsGoalEntity entity);
}
