package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.loot.lootsdk.models.orm.SharedPreferenceEntity;

@Dao
public interface LootSharedPreferencesDao extends BaseSynchronousDao<SharedPreferenceEntity> {
    @Query("DELETE FROM LootSharedPreferences WHERE `key` = :key")
    void deleteByKey(String key);

    @Query("SELECT * FROM LootSharedPreferences WHERE `key` = :key")
    LiveData<SharedPreferenceEntity> loadByKey(String key);

    @Query("SELECT `value` FROM LootSharedPreferences WHERE `key` = :key limit 1")
    String loadByKeySynchronously(String key);

    @Query("SELECT * FROM LootSharedPreferences")
    LiveData<List<SharedPreferenceEntity>> getSharedPreferences();

    @Query("SELECT * FROM LootSharedPreferences")
    List<SharedPreferenceEntity> getSharedPreferencesSynchronously();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void putSharedPreference(SharedPreferenceEntity entity);
}
