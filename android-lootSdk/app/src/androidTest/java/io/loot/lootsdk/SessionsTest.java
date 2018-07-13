package io.loot.lootsdk;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import io.loot.lootsdk.database.LootDatabase;
import io.loot.lootsdk.database.LootDatabaseWrapper;
import io.loot.lootsdk.database.daos.SessionDao;
import io.loot.lootsdk.models.entities.SessionRoomEntity;
import io.loot.lootsdk.utils.LiveDataUtils;

import static org.mockito.Mockito.*;

@RunWith(AndroidJUnit4.class)
public class SessionsTest {

    private Sessions sessions;

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    private SessionRoomEntity prepareEntity() {
        SessionRoomEntity entity = new SessionRoomEntity();
        entity.setAuthorizationToken("auth_token");
        entity.setEmail("email");
        entity.setUserId("user_id");

        return entity;
    }

    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        LootSDK lootSDK = mock(LootSDK.class);
        LootDatabase lootDatabase = mock(LootDatabase.class);

        SessionDao mockSessionDao = mock(SessionDao.class);
        when(mockSessionDao.getSession()).thenReturn(LiveDataUtils.emit(prepareEntity()));

        when(lootDatabase.sessionDao()).thenReturn(mockSessionDao);
        when(lootSDK.getDatabase()).thenReturn(new LootDatabaseWrapper(lootDatabase));

        when(lootSDK.getContext()).thenReturn(appContext);

        sessions = new Sessions(lootSDK);
    }

    @Test
    public void afterFetchUserShouldBeMarkedAsLoggedIn() throws Exception {
        sessions.fetch();
        Assert.assertTrue(sessions.isUserLoggedIn());
    }

}
