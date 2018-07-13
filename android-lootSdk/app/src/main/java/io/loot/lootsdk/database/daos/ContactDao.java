package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.loot.lootsdk.models.orm.ContactEntity;

@Dao
public interface ContactDao extends BaseSynchronousDao<ContactEntity>{

    @Query("SELECT * FROM Contact")
    LiveData<List<ContactEntity>> loadAll();

    @Query("SELECT * FROM Contact")
    List<ContactEntity> loadAllSynchronously();

    @Query("SELECT * FROM Contact WHERE accountNumber IS NULL OR accountNumber = '' OR sortCode IS NULL OR sortCode = ''")
    List<ContactEntity> loadLootContactsSynchronously();

    @Query("SELECT * FROM Contact WHERE accountNumber IS NOT NULL AND accountNumber != '' AND sortCode IS NOT NULL AND sortCode != ''")
    List<ContactEntity> loadSavedContactsSynchronously();

    @Query("DELETE FROM Contact")
    void deleteAll();

    @Query("DELETE FROM Contact WHERE contactId = :contactId")
    void deleteById(String contactId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ContactEntity> entity);
}
