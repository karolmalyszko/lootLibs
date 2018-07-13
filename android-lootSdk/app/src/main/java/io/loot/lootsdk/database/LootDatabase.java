package io.loot.lootsdk.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import io.loot.lootsdk.database.daos.AccountDao;
import io.loot.lootsdk.database.daos.AddressDao;
import io.loot.lootsdk.database.daos.AuthDeviceDao;
import io.loot.lootsdk.database.daos.BudgetDao;
import io.loot.lootsdk.database.daos.CardDao;
import io.loot.lootsdk.database.daos.CategoryDao;
import io.loot.lootsdk.database.daos.ContactDao;
import io.loot.lootsdk.database.daos.ContactTransactionDao;
import io.loot.lootsdk.database.daos.FeeOrLimitDao;
import io.loot.lootsdk.database.daos.ForgottenPasswordDao;
import io.loot.lootsdk.database.daos.LootSharedPreferencesDao;
import io.loot.lootsdk.database.daos.OnboardingUserDataDao;
import io.loot.lootsdk.database.daos.PersonalDetailsDao;
import io.loot.lootsdk.database.daos.PhonebookSyncDao;
import io.loot.lootsdk.database.daos.ProviderDao;
import io.loot.lootsdk.database.daos.RecipientsAndSendersDao;
import io.loot.lootsdk.database.daos.SavingGoalDao;
import io.loot.lootsdk.database.daos.ServerInfoDao;
import io.loot.lootsdk.database.daos.SessionDao;
import io.loot.lootsdk.database.daos.TopUpLimitsDao;
import io.loot.lootsdk.database.daos.TransactionDao;
import io.loot.lootsdk.database.daos.UserInfoDao;
import io.loot.lootsdk.models.entities.OnBoardingUserDataRoomEntity;
import io.loot.lootsdk.models.entities.SessionRoomEntity;
import io.loot.lootsdk.models.orm.AccountEntity;
import io.loot.lootsdk.models.orm.AddressEntity;
import io.loot.lootsdk.models.orm.AuthDeviceEntity;
import io.loot.lootsdk.models.orm.BudgetEntity;
import io.loot.lootsdk.models.orm.CardEntity;
import io.loot.lootsdk.models.orm.CategoryEntity;
import io.loot.lootsdk.models.orm.ContactEntity;
import io.loot.lootsdk.models.orm.ContactTransactionEntity;
import io.loot.lootsdk.models.orm.FeeOrLimitEntity;
import io.loot.lootsdk.models.orm.ForgottenPasswordEntity;
import io.loot.lootsdk.models.orm.PersonalDetailsEntity;
import io.loot.lootsdk.models.orm.PhonebookSyncEntity;
import io.loot.lootsdk.models.orm.ProviderEntity;
import io.loot.lootsdk.models.orm.RecipientsAndSendersEntity;
import io.loot.lootsdk.models.orm.SavingsGoalEntity;
import io.loot.lootsdk.models.orm.ServerInfoEntity;
import io.loot.lootsdk.models.orm.SharedPreferenceEntity;
import io.loot.lootsdk.models.orm.TopUpLimitsEntity;
import io.loot.lootsdk.models.orm.TransactionEntity;
import io.loot.lootsdk.models.orm.UserInfoEntity;

@TypeConverters({TimestampConverter.class})
@Database(entities = {OnBoardingUserDataRoomEntity.class, SessionRoomEntity.class, PersonalDetailsEntity.class, UserInfoEntity.class,
        AccountEntity.class, AddressEntity.class, BudgetEntity.class, ProviderEntity.class, AuthDeviceEntity.class, CardEntity.class,
        SavingsGoalEntity.class, CategoryEntity.class, TransactionEntity.class, FeeOrLimitEntity.class, ServerInfoEntity.class,
        ForgottenPasswordEntity.class, TopUpLimitsEntity.class, ContactEntity.class, ContactTransactionEntity.class, RecipientsAndSendersEntity.class, PhonebookSyncEntity.class, SharedPreferenceEntity.class}, version = 34)
public abstract class LootDatabase extends RoomDatabase {

    public abstract OnboardingUserDataDao onboardingUserDataDao();

    public abstract SessionDao sessionDao();

    public abstract PersonalDetailsDao personalDetailsDao();

    public abstract UserInfoDao userInfoDao();

    public abstract AccountDao accountDao();

    public abstract AddressDao addressDao();

    public abstract BudgetDao budgetDao();

    public abstract ProviderDao providerDao();

    public abstract AuthDeviceDao authDeviceDao();

    public abstract CardDao cardDao();

    public abstract SavingGoalDao savingGoalDao();

    public abstract CategoryDao categoryDao();

    public abstract TransactionDao transactionDao();

    public abstract FeeOrLimitDao feeOrLimitDao();

    public abstract ServerInfoDao serverInfoDao();

    public abstract ForgottenPasswordDao forgottenPasswordDao();

    public abstract TopUpLimitsDao topUpLimitsDao();

    public abstract ContactDao contactDao();

    public abstract ContactTransactionDao contactTransactionDao();

    public abstract RecipientsAndSendersDao recipientsAndSendersDao();

    public abstract PhonebookSyncDao phoneBookSyncDao();

    public abstract LootSharedPreferencesDao sharedPreferencesDao();
}
