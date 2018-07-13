package io.loot.lootsdk;


import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import io.loot.lootsdk.database.daos.ContactDao;
import io.loot.lootsdk.database.daos.ContactTransactionDao;
import io.loot.lootsdk.database.daos.PhonebookSyncDao;
import io.loot.lootsdk.database.daos.RecipientsAndSendersDao;
import io.loot.lootsdk.exceptions.NullPointerListenerException;
import io.loot.lootsdk.listeners.GetContactTransactionHistoryListener;
import io.loot.lootsdk.listeners.signup.ResendSMSListener;
import io.loot.lootsdk.listeners.user.ContactVerificationListener;
import io.loot.lootsdk.listeners.user.CreateUpdateContactListener;
import io.loot.lootsdk.listeners.user.DeleteContactListener;
import io.loot.lootsdk.listeners.user.GetContactsListener;
import io.loot.lootsdk.listeners.user.GetSyncedPhonebookContactsListener;
import io.loot.lootsdk.models.ActionResult;
import io.loot.lootsdk.models.Resource;
import io.loot.lootsdk.models.data.contacts.AllContacts;
import io.loot.lootsdk.models.data.contacts.Contact;
import io.loot.lootsdk.models.data.contacts.ContactTransaction;
import io.loot.lootsdk.models.data.contacts.ContactTransactionHistory;
import io.loot.lootsdk.models.data.contacts.SyncedContactInfo;
import io.loot.lootsdk.models.networking.contacts.ContactDetailsResponse;
import io.loot.lootsdk.models.networking.contacts.ContactListResponse;
import io.loot.lootsdk.models.networking.contacts.ContactTransactionHistoryResponse;
import io.loot.lootsdk.models.networking.contacts.ContactVerifyRequest;
import io.loot.lootsdk.models.networking.contacts.CreateUpdateContactRequest;
import io.loot.lootsdk.models.networking.contacts.CreateUpdateContactResponse;
import io.loot.lootsdk.models.networking.contacts.PhonebookSyncRequest;
import io.loot.lootsdk.models.networking.contacts.PhonebookSyncResponse;
import io.loot.lootsdk.models.orm.ContactEntity;
import io.loot.lootsdk.models.orm.ContactTransactionEntity;
import io.loot.lootsdk.models.orm.PhonebookSyncEntity;
import io.loot.lootsdk.models.orm.RecipientsAndSendersEntity;
import io.loot.lootsdk.networking.ApiResponse;
import io.loot.lootsdk.networking.LootApiInterface;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import io.loot.lootsdk.utils.ActionCompatHandler;
import io.loot.lootsdk.utils.AppExecutors;
import io.loot.lootsdk.utils.ResourceCompatHandler;
import okhttp3.ResponseBody;

public class Contacts {
    private LootSDK mLootSDK;
    private ContactDao mContactEntityDao;
    private PhonebookSyncDao mPhoneBookSyncDao;
    private ContactTransactionDao mContactTransactionDao;
    private RecipientsAndSendersDao mRecipientAndSenderDao;
    Contacts(LootSDK lootSDK) {
        mLootSDK = lootSDK;
        mContactEntityDao = mLootSDK.getDatabase().contactDao();
        mPhoneBookSyncDao = mLootSDK.getDatabase().phoneBookSyncDao();
        mContactTransactionDao = mLootSDK.getDatabase().contactTransactionDao();
        mRecipientAndSenderDao = mLootSDK.getDatabase().recipientsAndSendersDao();
    }

    public LiveData<Resource<AllContacts>> getContacts() {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkBoundResource<AllContacts, ContactListResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ContactListResponse item) {
                mContactEntityDao.deleteAll();
                mContactEntityDao.insert(ContactListResponse.parseToEntitiesObject(item));
            }

            @Override
            protected boolean shouldFetch(@Nullable AllContacts data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<AllContacts> loadFromDb() {
                final MutableLiveData<AllContacts> liveData = new MutableLiveData<>();
                //TODO refactor this to proper implementation of mediator with deleteAll option
                AppExecutors.get().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        AllContacts allContacts = new AllContacts();
                        List<ContactEntity> savedContactEntities = mContactEntityDao.loadSavedContactsSynchronously();
                        if (savedContactEntities != null) {
                            for (ContactEntity savedContactEntity : savedContactEntities) {
                                allContacts.getSavedDetails().add(ContactEntity.parseToDataObject(savedContactEntity, Contact.DetailsType.STANDARD_CONTACT));
                            }
                        }
                        List<ContactEntity> lootContactEntities = mContactEntityDao.loadLootContactsSynchronously();
                        if (lootContactEntities != null) {
                            for (ContactEntity contactEntity : lootContactEntities) {
                                allContacts.getFriendsOnLoot().add(ContactEntity.parseToDataObject(contactEntity, Contact.DetailsType.STANDARD_CONTACT));
                            }
                        }
                        liveData.postValue(allContacts);
                    }
                });

                return liveData;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ContactListResponse>> createCall() {
                return apiInterface.getContacts();
            }
        }.asLiveData();
    }

    @Deprecated
    public void getContacts(final GetContactsListener listener) {
        if (listener == null) {
            throw new NullPointerListenerException();
        }

        LiveData<Resource<AllContacts>> contactsLiveData = getContacts();
        new ResourceCompatHandler<AllContacts>(contactsLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onGetContactsError(errorMessage);
            }

            @Override
            public void onCachedDataReceived(AllContacts data) {
                listener.onGetCachedContacts(new AllContacts(data));
            }

            @Override
            public void onLiveDataReceived(AllContacts data) {
                listener.onGetContactsSuccess(new AllContacts(data));
            }
        }.handle();
    }

    public LiveData<Resource<String>> createContact(final CreateUpdateContactRequest request) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkResource<String, CreateUpdateContactResponse>(AppExecutors.get()) {
            @Override
            protected String proceedData(@NonNull CreateUpdateContactResponse item) {
                return CreateUpdateContactResponse.parseToDataObject(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CreateUpdateContactResponse>> createCall() {
                return apiInterface.createContact(request);
            }
        }.asLiveData();
    }

    @Deprecated
    public void createContact(CreateUpdateContactRequest createUpdateContactRequest, final CreateUpdateContactListener listener) {
        LiveData<Resource<String>> createContactLiveData = createContact(createUpdateContactRequest);

        new ResourceCompatHandler<String>(createContactLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onCreateUpdateContactError(errorMessage);
            }

            @Override
            public void onCachedDataReceived(String data) {

            }

            @Override
            public void onLiveDataReceived(String data) {
                listener.onCreateUpdateContactSuccess(data);
            }
        }.handle();
    }

    public LiveData<Resource<String>> updateContact(final String contactId, final CreateUpdateContactRequest request) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkResource<String, CreateUpdateContactResponse>(AppExecutors.get()) {
            @Override
            protected String proceedData(@NonNull CreateUpdateContactResponse item) {
                return CreateUpdateContactResponse.parseToDataObject(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CreateUpdateContactResponse>> createCall() {
                return apiInterface.updateContact(contactId, request);
            }
        }.asLiveData();
    }

    @Deprecated
    public void updateContact(String contactId, CreateUpdateContactRequest updateContactRequest, final CreateUpdateContactListener listener) {
        LiveData<Resource<String>> updateContactLiveData = updateContact(contactId, updateContactRequest);

        new ResourceCompatHandler<String>(updateContactLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onCreateUpdateContactError(errorMessage);
            }

            @Override
            public void onCachedDataReceived(String data) {

            }

            @Override
            public void onLiveDataReceived(String data) {
                listener.onCreateUpdateContactSuccess(data);
            }
        }.handle();
    }
    public LiveData<ActionResult> deleteContact(final String contactId) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkBoundAction<ResponseBody>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ResponseBody item) {
                mContactEntityDao.deleteById(contactId);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return apiInterface.deleteContact(contactId);
            }
        }.asLiveData();
    }

    @Deprecated
    public void deleteContact(final String contactId, final DeleteContactListener listener) {
        LiveData<ActionResult> deleteContactLiveData = deleteContact(contactId);
        new ActionCompatHandler(deleteContactLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onDeleteContactError(errorMessage);
            }

            @Override
            public void onSuccess() {
                listener.onDeleteContactSuccess();
            }
        }.handle();

    }

    public LiveData<ActionResult> verifyContact(final String contactId, final String code) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkBoundAction<ResponseBody>(AppExecutors.get()) {

            @Override
            protected void saveCallResult(@NonNull ResponseBody item) {

            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return apiInterface.verifyContact(contactId, new ContactVerifyRequest(code));
            }
        }.asLiveData();
    }

    @Deprecated
    public void verifyContact(final String contactId, final String code, final ContactVerificationListener listener) {
        LiveData<ActionResult> deleteContactLiveData = verifyContact(contactId, code);
        new ActionCompatHandler(deleteContactLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onContactVerificationError(errorMessage);
            }

            @Override
            public void onSuccess() {
                listener.onContactVerificationSuccess();
            }
        }.handle();
    }

    public LiveData<Resource<String>> createContactFromPayment(final String paymentId) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkResource<String, CreateUpdateContactResponse>(AppExecutors.get()) {
            @Override
            protected String proceedData(@NonNull CreateUpdateContactResponse item) {
                return CreateUpdateContactResponse.parseToDataObject(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CreateUpdateContactResponse>> createCall() {
                return apiInterface.createContactFromPayment(paymentId);
            }
        }.asLiveData();
    }

    @Deprecated
    public void createContactFromPayment(String paymentId, final CreateUpdateContactListener listener) {
        LiveData<Resource<String>>  createContactFromPaymentLiveData = createContactFromPayment(paymentId);
        new ResourceCompatHandler<String>(createContactFromPaymentLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onCreateUpdateContactError(errorMessage);
            }

            @Override
            public void onCachedDataReceived(String data) {

            }

            @Override
            public void onLiveDataReceived(String data) {
                listener.onCreateUpdateContactSuccess(data);
            }
        }.handle();
    }

    public LiveData<ActionResult> resendContactVerifySms(final String contactId) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();
        return new NetworkBoundAction<ResponseBody>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ResponseBody item) {

            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return apiInterface.resendContactVerifySms(contactId);
            }
        }.asLiveData();
    }

    @Deprecated
    public void resendContactVerifySms(String contactId, final ResendSMSListener listener) {
        LiveData<ActionResult> resendContactVerifySmsLiveData = resendContactVerifySms(contactId);

        new ActionCompatHandler(resendContactVerifySmsLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onError(errorMessage);
            }

            @Override
            public void onSuccess() {
                listener.onSMSSent();
            }
        }.handle();
    }

    public LiveData<Resource<ArrayList<SyncedContactInfo>>> syncPhonebookContacts(final PhonebookSyncRequest phonebookSyncRequestItems) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();
        return new NetworkBoundResource<ArrayList<SyncedContactInfo>, ArrayList<PhonebookSyncResponse>>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ArrayList<PhonebookSyncResponse> item) {
                mPhoneBookSyncDao.deleteAll();
                mPhoneBookSyncDao.insert(PhonebookSyncResponse.parseToEntitiesArray(item, phonebookSyncRequestItems.getPhoneNumbers()));
            }

            @Override
            protected boolean shouldFetch(@Nullable ArrayList<SyncedContactInfo> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<ArrayList<SyncedContactInfo>> loadFromDb() {
                return Transformations.map(mPhoneBookSyncDao.loadAll(), new Function<List<PhonebookSyncEntity>, ArrayList<SyncedContactInfo>>() {
                    @Override
                    public ArrayList<SyncedContactInfo> apply(List<PhonebookSyncEntity> input) {
                        return PhonebookSyncEntity.parseToDataArray(input);
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ArrayList<PhonebookSyncResponse>>> createCall() {
                return apiInterface.syncPhonebook(phonebookSyncRequestItems);
            }
        }.asLiveData();
    }

    @Deprecated
    public void syncPhonebookContacts(PhonebookSyncRequest phonebookSyncRequestItems, final GetSyncedPhonebookContactsListener listener) {
        LiveData<Resource<ArrayList<SyncedContactInfo>>> syncPhonebookContactsLiveData = syncPhonebookContacts(phonebookSyncRequestItems);
        new ResourceCompatHandler<ArrayList<SyncedContactInfo>>(syncPhonebookContactsLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onSyncedPhonebookContactsError(errorMessage);
            }

            @Override
            public void onCachedDataReceived(ArrayList<SyncedContactInfo> data) {

            }

            @Override
            public void onLiveDataReceived(ArrayList<SyncedContactInfo> data) {
                listener.onSyncedPhonebookContactsSuccess(data);
            }
        }.handle();
    }

    public LiveData<Resource<ContactTransactionHistory>> getLootContactHistory(final Contact contact) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();
        return new NetworkBoundResource<ContactTransactionHistory, ContactTransactionHistoryResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ContactTransactionHistoryResponse item) {
                ContactDetailsResponse contactDetailsResponse = new ContactDetailsResponse();
                contactDetailsResponse.setProfilePhoto(contact.getProfilePhoto());
                contactDetailsResponse.setContactId(contact.getContactId());
                contactDetailsResponse.setName(contact.getName());
                contactDetailsResponse.setSortCode(contact.getSortCode());
                contactDetailsResponse.setAccountNumber(contact.getAccountNumber());
                contactDetailsResponse.setHasPayments(contact.hasPayments());
                item.setContact(contactDetailsResponse);
                mContactTransactionDao.insert(ContactTransactionHistoryResponse.parseToEntityObjects(item));
            }

            @Override
            protected boolean shouldFetch(@Nullable ContactTransactionHistory data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<ContactTransactionHistory> loadFromDb() {
                return Transformations.map(mContactTransactionDao.loadById(contact.getContactId()), new Function<List<ContactTransactionEntity>, ContactTransactionHistory>() {
                    @Override
                    public ContactTransactionHistory apply(List<ContactTransactionEntity> input) {
                        ContactTransactionHistory result = new ContactTransactionHistory();
                        ArrayList<ContactTransaction> transactions = new ArrayList<>();

                        for (ContactTransactionEntity entity : input) {
                            transactions.add(entity.parseToDataObject());
                        }
                        result.setContact(contact);
                        result.setTransactions(transactions);
                        return result;
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ContactTransactionHistoryResponse>> createCall() {
                return apiInterface.getLootContactTransactionHistory(contact.getContactId());
            }
        }.asLiveData();
    }

    @Deprecated
    public void getLootContactHistory(Contact contact, final GetContactTransactionHistoryListener listener) {
        LiveData<Resource<ContactTransactionHistory>> getLootContactHistoryLiveData = getLootContactHistory(contact);
        new ResourceCompatHandler<ContactTransactionHistory>(getLootContactHistoryLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onGetContactTransactionHistoryError(errorMessage);
            }

            @Override
            public void onCachedDataReceived(ContactTransactionHistory data) {
                listener.onGetCachedContactTransactionHistorySuccess(data);
            }

            @Override
            public void onLiveDataReceived(ContactTransactionHistory data) {
                listener.onGetContactTransactionHistorySuccess(data);
            }
        }.handle();
    }

    public LiveData<Resource<ContactTransactionHistory>> getSDContactTransactionHistory(final Contact contact) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();
        return new NetworkBoundResource<ContactTransactionHistory, ContactTransactionHistoryResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ContactTransactionHistoryResponse item) {
                mContactTransactionDao.insert(ContactTransactionHistoryResponse.parseToEntityObjects(item));
            }

            @Override
            protected boolean shouldFetch(@Nullable ContactTransactionHistory data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<ContactTransactionHistory> loadFromDb() {
                return Transformations.map(mContactTransactionDao.loadById(contact.getContactId()), new Function<List<ContactTransactionEntity>, ContactTransactionHistory>() {
                    @Override
                    public ContactTransactionHistory apply(List<ContactTransactionEntity> input) {
                        ContactTransactionHistory result = new ContactTransactionHistory();
                        ArrayList<ContactTransaction> transactions = new ArrayList<>();

                        for (ContactTransactionEntity entity : input) {
                            transactions.add(entity.parseToDataObject());
                        }
                        result.setContact(contact);
                        result.setTransactions(transactions);
                        return result;
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ContactTransactionHistoryResponse>> createCall() {
                return apiInterface.getSDContactTransactionHistory(contact.getContactId());
            }
        }.asLiveData();
    }

    @Deprecated
    public void getSDContactTransactionHistory(Contact contact, final GetContactTransactionHistoryListener listener) {
        LiveData<Resource<ContactTransactionHistory>> syncPhonebookContactsLiveData = getSDContactTransactionHistory(contact);
        new ResourceCompatHandler<ContactTransactionHistory>(syncPhonebookContactsLiveData, listener) {
            @Override
            public void onError(String errorMessage) {
                listener.onGetContactTransactionHistoryError(errorMessage);
            }

            @Override
            public void onCachedDataReceived(ContactTransactionHistory data) {
                listener.onGetCachedContactTransactionHistorySuccess(data);
            }

            @Override
            public void onLiveDataReceived(ContactTransactionHistory data) {
                listener.onGetContactTransactionHistorySuccess(data);
            }
        }.handle();
    }

    public LiveData<Resource<ContactTransactionHistory>> getAccountDetailsTransactionHistory(final String accountNumber, final String sortCode, final String name) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();
        return new NetworkBoundResource<ContactTransactionHistory, ContactTransactionHistoryResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ContactTransactionHistoryResponse item) {
                RecipientsAndSendersEntity recipientsAndSendersEntity = new RecipientsAndSendersEntity(); //TODO we won't need that if transaction caching will be enabled
                recipientsAndSendersEntity.setName(name);
                recipientsAndSendersEntity.setAccountNumber(accountNumber);
                recipientsAndSendersEntity.setSortCode(sortCode);
                recipientsAndSendersEntity.setId(accountNumber+"-"+sortCode);
                mRecipientAndSenderDao.insert(recipientsAndSendersEntity);
                ArrayList<ContactTransactionEntity> list = ContactTransactionHistoryResponse.parseToEntityObjects(item);
                for (ContactTransactionEntity entity : list) {
                    entity.setPaymentDetails(recipientsAndSendersEntity);
                    entity.setPaymentDetailsId(recipientsAndSendersEntity.getId());
                }
                mContactTransactionDao.insert(list);
            }

            @Override
            protected boolean shouldFetch(@Nullable ContactTransactionHistory data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<ContactTransactionHistory> loadFromDb() {
                return Transformations.map(mContactTransactionDao.loadByAccountDetails(accountNumber, sortCode), new Function<List<ContactTransactionEntity>, ContactTransactionHistory>() {
                    @Override
                    public ContactTransactionHistory apply(List<ContactTransactionEntity> input) {
                        ContactTransactionHistory result = new ContactTransactionHistory();
                        ArrayList<ContactTransaction> transactions = new ArrayList<>();

                        for (ContactTransactionEntity entity : input) {
                            transactions.add(entity.parseToDataObject());
                        }

                        if (!input.isEmpty()) {
                            result.setContact(RecipientsAndSendersEntity.parseToDataObject(input.get(0).getPaymentDetails()));
                        }
                        result.setTransactions(transactions);
                        return result;
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ContactTransactionHistoryResponse>> createCall() {
                return apiInterface.getAccountDetailsTransactionHistory(accountNumber, sortCode);
            }
        }.asLiveData();
    }


    private boolean deleteFromCached(String contactId, ArrayList<Contact> cachedContactList) {
        if (contactId == null) {
            contactId = "";
        }
        for (Contact contact : cachedContactList) {
            if(contactId.equals(contact.getContactId())) {
                cachedContactList.remove(contact);
                return true;
            }
        }
        return false;
    }

}