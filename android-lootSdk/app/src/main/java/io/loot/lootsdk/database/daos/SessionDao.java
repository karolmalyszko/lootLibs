package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import io.loot.lootsdk.models.entities.SessionRoomEntity;

@Dao
public interface SessionDao extends BaseSynchronousDao<SessionRoomEntity> {

    @Query("SELECT * FROM Session LIMIT 1")
    LiveData<SessionRoomEntity> getSession();

    @Query("SELECT * FROM Session LIMIT 1")
    SessionRoomEntity getSessionSynchronously();

    @Query("DELETE FROM Session")
    void deleteAll();

}
