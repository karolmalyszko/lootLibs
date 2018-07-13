package io.loot.lootsdk;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.loot.lootsdk.database.LootDatabase;
import io.loot.lootsdk.database.LootDatabaseWrapper;
import io.loot.lootsdk.database.daos.TransactionDao;
import io.loot.lootsdk.models.networking.transactions.TransactionResponse;
import io.loot.lootsdk.models.orm.TransactionEntity;
import io.loot.lootsdk.utils.CommonUtils;

import static org.junit.Assert.assertEquals;


@RunWith(AndroidJUnit4.class)
public class TransactionDaoTests {

    @Rule
    public TestRule testRule = new InstantTaskExecutorRule();

    private Context appContext;
    private TransactionDao transactionDao;
    private LootDatabase database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getTargetContext(), LootDatabase.class).allowMainThreadQueries().build();
    ;

    private int transactionsCount;

    @Before
    public void setUp() {
        appContext = InstrumentationRegistry.getTargetContext();
        transactionDao = new LootDatabaseWrapper(database).transactionDao();
        populateDatabase();
    }

    static class TransactionWrapper implements Serializable {
        List<TransactionResponse> transactions;
    }

    private void populateDatabase() {
        Gson gson = new Gson();
        TransactionWrapper wrapper = gson.fromJson(CommonUtils.loadJsonFromAssets(appContext, "transactions.json"), TransactionWrapper.class);

        transactionsCount = wrapper.transactions.size();

        for (TransactionResponse response : wrapper.transactions) {
            transactionDao.insert(TransactionResponse.parseToEntityObject(response));
        }
    }

    @Test
    public void verifyTransactionDaoIsNotNull() throws Exception {
        Assert.assertNotNull(transactionDao);
    }

    @Test
    public void verifyAllObjectsFromFileAreInDatabase() throws Exception {
        transactionDao.loadAll().observeForever(new Observer<List<TransactionEntity>>() {
            @Override
            public void onChanged(@Nullable List<TransactionEntity> transactionEntities) {
                Assert.assertNotNull(transactionEntities);
                Assert.assertEquals(transactionEntities.size(), transactionsCount);
            }
        });
    }

    @Test
    public void transactionsCountFromWholeYearShouldEqualTransactionsCount() throws Exception {
        transactionDao.loadByDateRange("01-01-2017", "31-12-2017", 0, transactionsCount + 100).observeForever(new Observer<List<TransactionEntity>>() {
            @Override
            public void onChanged(@Nullable List<TransactionEntity> transactionEntities) {
                Assert.assertNotNull(transactionEntities);
                Assert.assertEquals(transactionEntities.size(), transactionsCount);
            }
        });
    }

    @Test
    public void wrapperTest() throws Exception {
        transactionDao.loadByDateRange("01-11-2017", "30-11-2017", 0, transactionsCount + 100).observeForever(new Observer<List<TransactionEntity>>() {
            @Override
            public void onChanged(@Nullable List<TransactionEntity> transactionEntities) {
                Assert.assertNotNull(transactionEntities);
                Assert.assertEquals(5, transactionEntities.size());
            }
        });
    }

}
