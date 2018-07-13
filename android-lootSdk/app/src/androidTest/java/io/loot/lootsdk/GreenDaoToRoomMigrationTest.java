package io.loot.lootsdk;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.loot.lootsdk.analytics.AnalyticsData;
import io.loot.lootsdk.database.LootDatabase;
import io.loot.lootsdk.database.LootDatabaseWrapper;
import io.loot.lootsdk.listeners.DatabaseCreationFailureListener;
import io.loot.lootsdk.models.data.userinfo.OnBoardingUserData;
import io.loot.lootsdk.models.entities.OnBoardingUserDataRoomEntity;
import io.loot.lootsdk.models.entities.SessionRoomEntity;
import io.loot.lootsdk.models.orm.SessionEntity;
import io.loot.lootsdk.utils.DBHelper;
import io.loot.lootsdk.utils.GreenDaoToRoomMigrationUtil;

@RunWith(AndroidJUnit4.class)
public class GreenDaoToRoomMigrationTest {

    private LootDatabaseWrapper lootRoomDatabase;
    private DBHelper dbHelper;

    @Before
    public void setUp() throws InterruptedException {
        Context appContext = InstrumentationRegistry.getTargetContext();

        dbHelper = DBHelper.getInstance();
        dbHelper.init(appContext);
        dbHelper.getSession(false).getSessionEntityDao().deleteAll();
        Thread.sleep(1500);

        lootRoomDatabase = new LootDatabaseWrapper(Room.inMemoryDatabaseBuilder(appContext, LootDatabase.class).allowMainThreadQueries().build());
    }

    @Test
    public void notNullableUserIdInEntityTest() throws Exception {
        SessionEntity nonNullableSessionEntity = new SessionEntity(0, "authToken", "email", "intercomHash",
                "onBoardingToken", false, "waitingListToken", "userId");

        dbHelper.getSession(false).getSessionEntityDao().insert(nonNullableSessionEntity);

//        GreenDaoToRoomMigrationUtil.migrateIfNeeded(dbHelper, lootRoomDatabase);
//
//        SessionRoomEntity fetchedEntity = lootRoomDatabase.sessionDao().getSessionSynchronously();
//        Assert.assertEquals("authToken", fetchedEntity.getAuthorizationToken());
//        Assert.assertEquals("email", fetchedEntity.getEmail());
//        Assert.assertEquals("intercomHash", fetchedEntity.getIntercomHash());
//        Assert.assertEquals("onBoardingToken", fetchedEntity.getOnBoardingToken());
//        Assert.assertEquals(false, fetchedEntity.isEmailVerified());
//        Assert.assertEquals("waitingListToken", fetchedEntity.getWaitingListToken());
//        Assert.assertEquals("userId", fetchedEntity.getUserId());
    }

    @Test
    public void nullableUserIdInEntityTest() throws Exception {
        SessionEntity nonNullableSessionEntity = new SessionEntity(0, "authToken", "email", "intercomHash",
                "onBoardingToken", false, "waitingListToken", null);

        dbHelper.getSession(false).getSessionEntityDao().insert(nonNullableSessionEntity);

//        GreenDaoToRoomMigrationUtil.migrateIfNeeded(dbHelper, lootRoomDatabase);
//
//        SessionRoomEntity fetchedEntity = lootRoomDatabase.sessionDao().getSessionSynchronously();
//        Assert.assertEquals("authToken", fetchedEntity.getAuthorizationToken());
//        Assert.assertEquals("email", fetchedEntity.getEmail());
//        Assert.assertEquals("intercomHash", fetchedEntity.getIntercomHash());
//        Assert.assertEquals("onBoardingToken", fetchedEntity.getOnBoardingToken());
//        Assert.assertEquals(false, fetchedEntity.isEmailVerified());
//        Assert.assertEquals("waitingListToken", fetchedEntity.getWaitingListToken());
//        Assert.assertEquals("", fetchedEntity.getUserId());
    }

}
