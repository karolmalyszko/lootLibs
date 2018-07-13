package io.loot.lootsdk.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.loot.lootsdk.models.data.userinfo.PersonalDetails;
import io.loot.lootsdk.models.orm.PersonalDetailsEntity;

@Dao
public interface PersonalDetailsDao extends BaseSynchronousDao<PersonalDetailsEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPersonalDetails(PersonalDetailsEntity personalDetails);

    @Query("SELECT * FROM PersonalDetails LIMIT 1")
    PersonalDetailsEntity getPersonalDetailsSynchronously();

    @Query("SELECT * FROM PersonalDetails LIMIT 1")
    LiveData<PersonalDetailsEntity> getPersonalDetails();

    @Query("SELECT * FROM PersonalDetails")
    List<PersonalDetailsEntity> getAllPersonalDetails();

    @Query("SELECT * FROM PersonalDetails WHERE entityId = :id")
    PersonalDetailsEntity getPersonalDetailsByIdSynchronously(long id);

    @Query("SELECT * FROM PersonalDetails WHERE entityId = :id")
    LiveData<PersonalDetailsEntity> getPersonalDetailsById(long id);

    @Query("DELETE FROM PersonalDetails")
    void deleteAll();
}
