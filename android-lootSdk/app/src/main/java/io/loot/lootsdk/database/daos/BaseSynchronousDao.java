package io.loot.lootsdk.database.daos;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import java.util.List;

public interface BaseSynchronousDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T... entities);

    @Delete
    void delete(T entity);

}
