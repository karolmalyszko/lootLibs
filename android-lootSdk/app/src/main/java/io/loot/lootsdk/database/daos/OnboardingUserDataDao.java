package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.loot.lootsdk.models.entities.OnBoardingUserDataRoomEntity;

@Dao
public interface OnboardingUserDataDao extends BaseSynchronousDao<OnBoardingUserDataRoomEntity> {

    @Query("SELECT * FROM OnboardingUserData")
    List<OnBoardingUserDataRoomEntity> getAll();

    @Query("SELECT * FROM OnboardingUserData LIMIT 1")
    LiveData<OnBoardingUserDataRoomEntity> load();

    @Insert
    void insertAll(OnBoardingUserDataRoomEntity... userDatas);

    @Query("DELETE FROM OnboardingUserData")
    void deleteAll();
}
