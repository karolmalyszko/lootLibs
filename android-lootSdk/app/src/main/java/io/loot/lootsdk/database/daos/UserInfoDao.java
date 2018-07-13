package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import io.loot.lootsdk.models.data.userinfo.UserInfo;
import io.loot.lootsdk.models.orm.UserInfoEntity;

@Dao
public interface UserInfoDao extends BaseSynchronousDao<UserInfoEntity> {

    @Query("SELECT * FROM UserInfo LIMIT 1")
    LiveData<UserInfoEntity> load();

    @Query("SELECT * FROM UserInfo LIMIT 1")
    UserInfoEntity loadSynchronously();

    @Query("DELETE FROM UserInfo")
    void deleteAll();
}
