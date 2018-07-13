package io.loot.lootsdk.database;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import io.loot.lootsdk.database.utils.DatabaseAccessExecutor;
import io.loot.lootsdk.models.entities.SessionRoomEntity;
import io.loot.lootsdk.models.orm.AccountEntity;
import io.loot.lootsdk.models.orm.BudgetEntity;
import io.loot.lootsdk.models.orm.ContactEntity;
import io.loot.lootsdk.models.orm.ContactTransactionEntity;
import io.loot.lootsdk.models.orm.PersonalDetailsEntity;
import io.loot.lootsdk.models.orm.PhonebookSyncEntity;
import io.loot.lootsdk.models.orm.ProviderEntity;
import io.loot.lootsdk.models.orm.RecipientsAndSendersEntity;
import io.loot.lootsdk.models.orm.SavingsGoalEntity;
import io.loot.lootsdk.models.orm.SharedPreferenceEntity;
import io.loot.lootsdk.models.orm.TransactionEntity;
import io.loot.lootsdk.models.orm.UserInfoEntity;
import io.loot.lootsdk.utils.AppExecutors;

public class LootDatabaseWrapper extends LootDatabase {

    private LootDatabase mLootDatabaseDelegate;

    public LootDatabaseWrapper(LootDatabase database) {
        mLootDatabaseDelegate = database;
    }

    @Override
    public OnboardingUserDataDao onboardingUserDataDao() {
        return mLootDatabaseDelegate.onboardingUserDataDao();
    }

    @Override
    public SessionDao sessionDao() {
       return new SessionDao() {
           @Override
           public LiveData<SessionRoomEntity> getSession() {
               return mLootDatabaseDelegate.sessionDao().getSession();
           }

           @Override
           public SessionRoomEntity getSessionSynchronously() {
               return mLootDatabaseDelegate.sessionDao().getSessionSynchronously();
           }

           @Override
           public void deleteAll() {
               mLootDatabaseDelegate.sessionDao().deleteAll();
           }

           @Override
           public void insert(SessionRoomEntity... sessions) {
               DatabaseAccessExecutor.with(mLootDatabaseDelegate.sessionDao()).insert(sessions);
           }

           @Override
           public void delete(SessionRoomEntity entity) {
               DatabaseAccessExecutor.with(mLootDatabaseDelegate.sessionDao()).delete(entity);
           }
       };
    }

    @Override
    public PersonalDetailsDao personalDetailsDao() {
        return new PersonalDetailsDao() {
            @Override
            public void insert(PersonalDetailsEntity... entities) {
                mLootDatabaseDelegate.personalDetailsDao().insert(entities);
            }

            @Override
            public void delete(PersonalDetailsEntity entity) {
                DatabaseAccessExecutor.with(mLootDatabaseDelegate.personalDetailsDao()).delete(entity);
            }

            @Override
            public void insertPersonalDetails(PersonalDetailsEntity personalDetails) {
                addressDao().insert(personalDetails.getAddress());

                mLootDatabaseDelegate.personalDetailsDao().insertPersonalDetails(personalDetails);
            }

            @Override
            public PersonalDetailsEntity getPersonalDetailsSynchronously() {
                return mLootDatabaseDelegate.personalDetailsDao().getPersonalDetailsSynchronously();
            }

            @Override
            public LiveData<PersonalDetailsEntity> getPersonalDetails() {
                return mLootDatabaseDelegate.personalDetailsDao().getPersonalDetails();
            }

            @Override
            public List<PersonalDetailsEntity> getAllPersonalDetails() {
                return mLootDatabaseDelegate.personalDetailsDao().getAllPersonalDetails();
            }

            @Override
            public PersonalDetailsEntity getPersonalDetailsByIdSynchronously(long id) {
                return mLootDatabaseDelegate.personalDetailsDao().getPersonalDetailsByIdSynchronously(id);
            }

            @Override
            public LiveData<PersonalDetailsEntity> getPersonalDetailsById(long id) {
                return mLootDatabaseDelegate.personalDetailsDao().getPersonalDetailsById(id);
            }

            @Override
            public void deleteAll() {
                mLootDatabaseDelegate.personalDetailsDao().deleteAll();
            }
        };
    }

    @Override
    public UserInfoDao userInfoDao() {
        return new UserInfoDao() {
            @Override
            public void insert(UserInfoEntity... entities) {
                for (UserInfoEntity entity : entities) {
                    personalDetailsDao().insertPersonalDetails(entity.getPersonalDetails());
                    addressDao().insert(entity.getPersonalDetails().getAddress());
                }

                DatabaseAccessExecutor.with(mLootDatabaseDelegate.userInfoDao()).insert(entities);
            }

            @Override
            public void delete(UserInfoEntity entity) {
                DatabaseAccessExecutor.with(mLootDatabaseDelegate.userInfoDao()).delete(entity);
            }

            @Override
            public LiveData<UserInfoEntity> load() {
                return mLootDatabaseDelegate.userInfoDao().load();
            }

            @Override
            public UserInfoEntity loadSynchronously() {
                return mLootDatabaseDelegate.userInfoDao().loadSynchronously();
            }

            @Override
            public void deleteAll() {
                mLootDatabaseDelegate.userInfoDao().deleteAll();
            }
        };
    }

    @Override
    public AccountDao accountDao() {
        return new AccountDao() {
            @Override
            public void insert(AccountEntity... entities) {
                for (AccountEntity entity : entities) {
                    mLootDatabaseDelegate.accountDao().insert(entity);
                    userInfoDao().insert(entity.getUserInfo());
                    providerDao().insert(entity.getProvider());
                }

            }

            @Override
            public void delete(AccountEntity entity) {
                DatabaseAccessExecutor.with(mLootDatabaseDelegate.accountDao()).delete(entity);
            }

            @Override
            public LiveData<List<AccountEntity>> loadAll() {
                return mLootDatabaseDelegate.accountDao().loadAll();
            }

            @Override
            public List<AccountEntity> loadAllSynchronously() {
                return mLootDatabaseDelegate.accountDao().loadAllSynchronously();
            }

            @Override
            public void deleteAll() {
                mLootDatabaseDelegate.accountDao().deleteAll();
            }
        };
    }

    @Override
    public AddressDao addressDao() {
        return mLootDatabaseDelegate.addressDao();
    }

    @Override
    public BudgetDao budgetDao() {
        return new BudgetDao() {
            @Override
            public LiveData<BudgetEntity> loadBudget() {
                return mLootDatabaseDelegate.budgetDao().loadBudget();
            }

            @Override
            public BudgetEntity loadBudgetSynchronously() {
                return mLootDatabaseDelegate.budgetDao().loadBudgetSynchronously();
            }

            @Override
            public void deleteAll() {
                AppExecutors.get().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mLootDatabaseDelegate.budgetDao().deleteAll();
                    }
                });
            }

            @Override
            public void insert(BudgetEntity... entities) {
                DatabaseAccessExecutor.with(mLootDatabaseDelegate.budgetDao()).insert(entities);
            }

            @Override
            public void delete(BudgetEntity entity) {
                DatabaseAccessExecutor.with(mLootDatabaseDelegate.budgetDao()).delete(entity);
            }
        };
    }

    @Override
    public ProviderDao providerDao() {
        return new ProviderDao() {
            @Override
            public void insert(ProviderEntity... entities) {
                DatabaseAccessExecutor.with(mLootDatabaseDelegate.providerDao()).insert(entities);
            }

            @Override
            public void delete(ProviderEntity entity) {
                DatabaseAccessExecutor.with(mLootDatabaseDelegate.providerDao()).delete(entity);
            }

            @Override
            public LiveData<List<ProviderEntity>> loadAll() {
                return mLootDatabaseDelegate.providerDao().loadAll();
            }

            @Override
            public ProviderEntity loadByIdSynchronously(String providerId) {
                return mLootDatabaseDelegate.providerDao().loadByIdSynchronously(providerId);
            }

            @Override
            public void deleteAll() {
                mLootDatabaseDelegate.providerDao().deleteAll();
            }

        };
    }

    @Override
    public AuthDeviceDao authDeviceDao() {
        return mLootDatabaseDelegate.authDeviceDao();
    }

    @Override
    public CardDao cardDao() {
        return mLootDatabaseDelegate.cardDao();
    }

    @Override
    public SavingGoalDao savingGoalDao() {
        return new SavingGoalDao() {
            @Override
            public LiveData<List<SavingsGoalEntity>> loadAll() {
                return mLootDatabaseDelegate.savingGoalDao().loadAll();
            }

            @Override
            public int deleteAll() {
                return mLootDatabaseDelegate.savingGoalDao().deleteAll();
            }

            @Override
            public void deleteById(final String id) {
                AppExecutors.get().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mLootDatabaseDelegate.savingGoalDao().deleteById(id);
                    }
                });
            }

            @Override
            public LiveData<SavingsGoalEntity> loadById(final String id) {
                return mLootDatabaseDelegate.savingGoalDao().loadById(id);
            }

            @Override
            public void update(final SavingsGoalEntity entity) {
                AppExecutors.get().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mLootDatabaseDelegate.savingGoalDao().delete(entity);
                    }
                });
            }

            @Override
            public void insert(SavingsGoalEntity... entities) {
                mLootDatabaseDelegate.savingGoalDao().insert(entities);
            }

            @Override
            public void delete(SavingsGoalEntity entity) {
                mLootDatabaseDelegate.savingGoalDao().delete(entity);
            }
        };
    }

    @Override
    public CategoryDao categoryDao() {
        return mLootDatabaseDelegate.categoryDao();
    }

    @Override
    public TransactionDao transactionDao() {
        return new TransactionDao() {
            @Override
            public LiveData<List<TransactionEntity>> loadAll() {
                return mLootDatabaseDelegate.transactionDao().loadAll();
            }

            @Override
            public LiveData<List<TransactionEntity>> loadByDateRange(String from, String to, int page, int limit) {
                SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy");

                Date dateFrom = new Date();
                Date dateTo = new Date();

                try {
                    dateFrom = sdfOut.parse(from);
                    dateTo = sdfOut.parse(to);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return loadByDateRange(dateFrom, dateTo, page, limit);
            }

            @Override
            public LiveData<List<TransactionEntity>> loadByDateRange(Date from, Date to, int page, int limit) {
                Date dateToInclusive = new DateTime(to).plusDays(1).toDate();
                return mLootDatabaseDelegate.transactionDao().loadByDateRange(from, dateToInclusive, page, limit);
            }

            @Override
            public DataSource.Factory<Integer, TransactionEntity> loadByDateRangePagination(String from, String to, int page, int limit) {
                SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy");

                Date dateFrom = new Date();
                Date dateTo = new Date();

                try {
                    dateFrom = sdfOut.parse(from);
                    dateTo = sdfOut.parse(to);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return loadByDateRangePagination(dateFrom, dateTo, page, limit);
            }

            @Override
            public DataSource.Factory<Integer, TransactionEntity> loadByDateRangePagination(Date from, Date to, int page, int limit) {
                Date dateToInclusive = new DateTime(to).plusDays(1).toDate();
                return mLootDatabaseDelegate.transactionDao().loadByDateRangePagination(from, dateToInclusive, page, limit);
            }

            @Override
            public void deleteAll() {
                mLootDatabaseDelegate.transactionDao().deleteAll();
            }

            @Override
            public void insert(TransactionEntity... entities) {
                mLootDatabaseDelegate.transactionDao().insert(entities);
            }

            @Override
            public void delete(TransactionEntity entity) {
                mLootDatabaseDelegate.transactionDao().delete(entity);
            }
        };
    }

    @Override
    public ContactDao contactDao() {
        return new ContactDao() {
            @Override
            public LiveData<List<ContactEntity>> loadAll() {
                return mLootDatabaseDelegate.contactDao().loadAll();
            }

            @Override
            public List<ContactEntity> loadAllSynchronously() {
                return mLootDatabaseDelegate.contactDao().loadAllSynchronously();
            }

            @Override
            public List<ContactEntity> loadLootContactsSynchronously() {
                return  mLootDatabaseDelegate.contactDao().loadLootContactsSynchronously();
            }

            @Override
            public List<ContactEntity> loadSavedContactsSynchronously() {
                return  mLootDatabaseDelegate.contactDao().loadSavedContactsSynchronously();
            }

            @Override
            public void deleteAll() {
                AppExecutors.get().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mLootDatabaseDelegate.contactDao().deleteAll();
                    }
                });
            }

            @Override
            public void deleteById(final String id) {
                AppExecutors.get().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mLootDatabaseDelegate.contactDao().deleteById(id);
                    }
                });
            }

            @Override
            public void insert(final List<ContactEntity> entity) {
                AppExecutors.get().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mLootDatabaseDelegate.contactDao().insert(entity);
                    }
                });
            }

            @Override
            public void insert(ContactEntity... entities) {
                mLootDatabaseDelegate.contactDao().insert(entities);
            }

            @Override
            public void delete(ContactEntity entity) {
                mLootDatabaseDelegate.contactDao().delete(entity);
            }
        };
    }

    @Override
    public ContactTransactionDao contactTransactionDao() {
        return new ContactTransactionDao() {
            @Override
            public LiveData<List<ContactTransactionEntity>> loadById(String contactId) {
                return mLootDatabaseDelegate.contactTransactionDao().loadById(contactId);
            }

            @Override
            public LiveData<List<ContactTransactionEntity>> loadByAccountDetails(String accountNumber, String sortCode) {
                return mLootDatabaseDelegate.contactTransactionDao().loadByAccountDetails(accountNumber, sortCode);
            }

            @Override
            public void deleteAll() {
                AppExecutors.get().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mLootDatabaseDelegate.contactTransactionDao().deleteAll();
                    }
                });
            }

            @Override
            public void deleteById(final String id) {
                AppExecutors.get().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mLootDatabaseDelegate.contactTransactionDao().deleteById(id);
                    }
                });
            }

            @Override
            public void insert(final List<ContactTransactionEntity> entity) {
                AppExecutors.get().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        mLootDatabaseDelegate.contactTransactionDao().insert(entity);
                    }
                });
            }

            @Override
            public void insert(ContactTransactionEntity... entities) {
                mLootDatabaseDelegate.contactTransactionDao().insert(entities);
            }

            @Override
            public void delete(ContactTransactionEntity entity) {
                mLootDatabaseDelegate.contactTransactionDao().delete(entity);
            }
        };
    }


    @Override
    public RecipientsAndSendersDao recipientsAndSendersDao() {
        return new RecipientsAndSendersDao() {
            @Override
            public LiveData<List<RecipientsAndSendersEntity>> loadById(String paymentId) {
                return mLootDatabaseDelegate.recipientsAndSendersDao().loadById(paymentId);
            }

            @Override
            public void deleteAll() {
                mLootDatabaseDelegate.recipientsAndSendersDao().deleteAll();
            }

            @Override
            public void deleteById(String paymentId) {
                mLootDatabaseDelegate.recipientsAndSendersDao().deleteById(paymentId);
            }

            @Override
            public void insert(List<RecipientsAndSendersEntity> entity) {
                mLootDatabaseDelegate.recipientsAndSendersDao().insert(entity);
            }

            @Override
            public void insert(RecipientsAndSendersEntity... entities) {
                mLootDatabaseDelegate.recipientsAndSendersDao().insert(entities);
            }

            @Override
            public void delete(RecipientsAndSendersEntity entity) {
                mLootDatabaseDelegate.recipientsAndSendersDao().delete(entity);
            }
        };
    }

    @Override
    public PhonebookSyncDao phoneBookSyncDao() {
        return new PhonebookSyncDao() {
            @Override
            public LiveData<List<PhonebookSyncEntity>> loadAll() {
                return mLootDatabaseDelegate.phoneBookSyncDao().loadAll();
            }

            @Override
            public List<PhonebookSyncEntity> loadAllSynchronously() {
                return mLootDatabaseDelegate.phoneBookSyncDao().loadAllSynchronously();
            }

            @Override
            public void deleteAll() {
                mLootDatabaseDelegate.phoneBookSyncDao().deleteAll();
            }

            @Override
            public void insert(List<PhonebookSyncEntity> entity) {
                mLootDatabaseDelegate.phoneBookSyncDao().insert(entity);
            }

            @Override
            public void insert(PhonebookSyncEntity... entities) {
                mLootDatabaseDelegate.phoneBookSyncDao().insert(entities);
            }

            @Override
            public void delete(PhonebookSyncEntity entity) {
                mLootDatabaseDelegate.phoneBookSyncDao().delete(entity);
            }
        };
    }

    @Override
    public LootSharedPreferencesDao sharedPreferencesDao() {
        return new LootSharedPreferencesDao() {
            @Override
            public void deleteByKey(String key) {
                mLootDatabaseDelegate.sharedPreferencesDao().deleteByKey(key);
            }

            @Override
            public LiveData<SharedPreferenceEntity> loadByKey(String key) {
                return mLootDatabaseDelegate.sharedPreferencesDao().loadByKey(key);
            }

            @Override
            public String loadByKeySynchronously(String key) {
                return mLootDatabaseDelegate.sharedPreferencesDao().loadByKeySynchronously(key);
            }

            @Override
            public LiveData<List<SharedPreferenceEntity>> getSharedPreferences() {
                return mLootDatabaseDelegate.sharedPreferencesDao().getSharedPreferences();
            }

            @Override
            public List<SharedPreferenceEntity> getSharedPreferencesSynchronously() {
                return mLootDatabaseDelegate.sharedPreferencesDao().getSharedPreferencesSynchronously();
            }

            @Override
            public void putSharedPreference(SharedPreferenceEntity entity) {
                mLootDatabaseDelegate.sharedPreferencesDao().putSharedPreference(entity);
            }

            @Override
            public void insert(SharedPreferenceEntity... entities) {
                mLootDatabaseDelegate.sharedPreferencesDao().insert(entities);
            }

            @Override
            public void delete(SharedPreferenceEntity entity) {
                mLootDatabaseDelegate.sharedPreferencesDao().delete(entity);
            }
        };
    }

    @Override
    public FeeOrLimitDao feeOrLimitDao() {
        return mLootDatabaseDelegate.feeOrLimitDao();
    }

    @Override
    public ServerInfoDao serverInfoDao() {
        return mLootDatabaseDelegate.serverInfoDao();
    }

    @Override
    public ForgottenPasswordDao forgottenPasswordDao() {
        return mLootDatabaseDelegate.forgottenPasswordDao();
    }

    @Override
    public TopUpLimitsDao topUpLimitsDao() {
        return mLootDatabaseDelegate.topUpLimitsDao();
    }

    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }

    @Override
    protected InvalidationTracker createInvalidationTracker() {
        return null;
    }

    @Override
    public void clearAllTables() {
        sessionDao().deleteAll();
        transactionDao().deleteAll();
        accountDao().deleteAll();
        addressDao().deleteAll();
        budgetDao().deleteAll();
        cardDao().deleteAll();
        feeOrLimitDao().deleteAll();
        categoryDao().deleteAll();
        authDeviceDao().deleteAll();
        onboardingUserDataDao().deleteAll();
        userInfoDao().deleteAll();
        forgottenPasswordDao().deleteAll();
        personalDetailsDao().deleteAll();
        serverInfoDao().deleteAll();
        providerDao().deleteAll();
        savingGoalDao().deleteAll();
        topUpLimitsDao().deleteAll();
        contactDao().deleteAll();
        contactTransactionDao().deleteAll();
        recipientsAndSendersDao().deleteAll();
        phoneBookSyncDao().deleteAll();
    }
}
