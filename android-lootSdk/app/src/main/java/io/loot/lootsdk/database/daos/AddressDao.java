package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.loot.lootsdk.models.orm.AddressEntity;

@Dao
public interface AddressDao extends BaseSynchronousDao<AddressEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(AddressEntity addressEntity);

    @Query("SELECT * FROM Address WHERE entityId = :id")
    AddressEntity loadByIdSynchronously(long id);

    @Query("SELECT * FROM Address WHERE entityId = :id")
    LiveData<AddressEntity> loadById(long id);

    @Query("SELECT * FROM Address")
    List<AddressEntity> findAll();

    @Query("DELETE FROM Address")
    void deleteAll();
}
