package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

import io.loot.lootsdk.models.data.category.Category;
import io.loot.lootsdk.models.orm.AccountEntity;
import io.loot.lootsdk.models.orm.CategoryEntity;

@Dao
public interface CategoryDao extends BaseSynchronousDao<CategoryEntity> {

    @Query("SELECT * FROM Category")
    LiveData<List<CategoryEntity>> loadAll();

    @Query("DELETE FROM Category")
    void deleteAll();
}
