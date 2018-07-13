package io.loot.lootsdk;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import io.loot.lootsdk.models.ActionResult;
import io.loot.lootsdk.models.ErrorResponse;
import io.loot.lootsdk.models.Resource;
import io.loot.lootsdk.models.data.category.Category;
import io.loot.lootsdk.models.data.transactions.Spending;
import io.loot.lootsdk.models.data.transactions.Transaction;
import io.loot.lootsdk.models.networking.ErrorExtractor;
import io.loot.lootsdk.models.networking.category.CategorisationRequest;
import io.loot.lootsdk.models.networking.transactions.IncludeExcludeResponse;
import io.loot.lootsdk.models.networking.transactions.SpendingResponse;
import io.loot.lootsdk.models.networking.transactions.TransactionListResponse;
import io.loot.lootsdk.models.networking.transactions.TransactionResponse;
import io.loot.lootsdk.networking.ApiResponse;
import io.loot.lootsdk.networking.LootApiInterface;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import io.loot.lootsdk.utils.AppExecutors;
import okhttp3.ResponseBody;

public class Transactions {
    private static String NO_TRANSACTION_IN_RANGE = "NO_TRANSACTION_IN_RANGE";
    private static String CANT_GENERATE_STATEMENT = "CANT_GENERATE_STATEMENT";
    private LootSDK mLootSDK;
    private LootApiInterface mApiInterface;

    Transactions(LootSDK lootSDK) {
        mLootSDK = lootSDK;
    }

     public LiveData<Resource<PaginatedTransactionsHolder>> getTransactionsOnPage(final String userId, final String from, final String to, final int page, final int limit) {
        initApiInterface();

        return new NetworkResource<PaginatedTransactionsHolder, TransactionListResponse>(AppExecutors.get()) {
            @Override
            protected PaginatedTransactionsHolder proceedData(@NonNull TransactionListResponse item) {
                List<Transaction> transactions = new ArrayList<>();

                for (TransactionResponse response : item.getTransactions()) {
                    transactions.add(TransactionResponse.parseToDataObject(response));
                }

                return new PaginatedTransactionsHolder(transactions, item.hasNextPage());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<TransactionListResponse>> createCall() {
                return mApiInterface.getTransactionsOnPage(userId, from, to, page, limit);
            }
        }.asLiveData();
    }

    public LiveData<Resource<PagedList<Transaction>>> getTransactionsPaginated(final String userId, final String from, final String to, final int page, final int limit) {
        initApiInterface();
        PagedList.Config pagingConfig = pagingConfig = new PagedList.Config.Builder()
                .setPageSize(limit)
                .setPrefetchDistance(Math.round(limit / 2))
                .setEnablePlaceholders(true)
                .build();
        TransactionsDataFactory factory = new TransactionsDataFactory(mApiInterface, mLootSDK, from, to, page, limit);


        final LiveData<PagedList<Transaction>> transactionLiveData = new LivePagedListBuilder(factory, pagingConfig)
                .setFetchExecutor(Executors.newFixedThreadPool(3))
                .build();

        final MediatorLiveData<Resource<PagedList<Transaction>>> mediatorLiveData = new MediatorLiveData<Resource<PagedList<Transaction>>>();
        final Observer<PagedList<Transaction>> transactionLiveDataObserver = new Observer<PagedList<Transaction>>() {
            @Override
            public void onChanged(@Nullable PagedList<Transaction> transactions) {
                if (transactions != null) {
                    mediatorLiveData.setValue(Resource.success(transactions));
                }
            }
        };
        mediatorLiveData.addSource(transactionLiveData, transactionLiveDataObserver);
        mediatorLiveData.addSource(Transformations.switchMap(factory.datasourceLiveData, new Function<TransactionsDataSource, LiveData<TransactionsDataSource.ResourceStatus>>() {
            @Override
            public LiveData<TransactionsDataSource.ResourceStatus> apply(TransactionsDataSource input) {
                return input.resourceStatusLiveData;
            }
        }), new Observer<TransactionsDataSource.ResourceStatus>() {
            @Override
            public void onChanged(@Nullable TransactionsDataSource.ResourceStatus resourceStatus) {
                mediatorLiveData.removeSource(transactionLiveData);
                if (resourceStatus.status.equals(Resource.Status.LOADING)) {
                    mediatorLiveData.setValue(Resource.<PagedList<Transaction>>loading(null));
                } else if (resourceStatus.status.equals(Resource.Status.ERROR)) {
                    mediatorLiveData.setValue(Resource.<PagedList<Transaction>>error(resourceStatus.message, resourceStatus.code, null));
                } else if (resourceStatus.status.equals(Resource.Status.SUCCESS)) {
                    mediatorLiveData.addSource(transactionLiveData, transactionLiveDataObserver);
                }
            }
        });
        return mediatorLiveData;
    }

    public LiveData<Resource<List<Transaction>>> getAllTransactions(final String userId) {
        initApiInterface();

        return new NetworkResource<List<Transaction>, TransactionListResponse>(AppExecutors.get()) {
            @Override
            protected List<Transaction> proceedData(@NonNull TransactionListResponse item) {
                return TransactionListResponse.parseToDataObject(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<TransactionListResponse>> createCall() {
                return mApiInterface.getAllTransactions(userId);
            }
        }.asLiveData();
    }

    // TODO : After reimplementing Loot App to AAC we should enable caching with PaginateLibrary
    /* public LiveData<Resource<PaginatedTransactionsHolder>> getTransactionsOnPage(final String userId, final String from, final String to, final int page, final int limit) {
        initApiInterface();

        final int databaseConvertedPage = (page > 0) ? page - 1 : 0;

        return new NetworkBoundResource<PaginatedTransactionsHolder, TransactionListResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull TransactionListResponse item) {
                for (TransactionEntity entity : TransactionListResponse.parseToEntityObject(item)) {
                    mTransactionDao.insert(entity);
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable PaginatedTransactionsHolder data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<PaginatedTransactionsHolder> loadFromDb() {
                final MediatorLiveData<List<TransactionEntity>> entitiesMerger = new MediatorLiveData<>();
                final MutableLiveData<PaginatedTransactionsHolder> paginationLiveData = new MutableLiveData<>();
                entitiesMerger.addSource(mTransactionDao.loadByDateRange(from, to, databaseConvertedPage, limit), new Observer<List<TransactionEntity>>() {
                    @Override
                    public void onChanged(@Nullable final List<TransactionEntity> currentPageEntities) {
                        entitiesMerger.addSource(mTransactionDao.loadByDateRange(from, to, databaseConvertedPage + 1, limit), new Observer<List<TransactionEntity>>() {
                            @Override
                            public void onChanged(@Nullable List<TransactionEntity> nextPageEntities) {
                                boolean hasNextPage = false;

                                if (nextPageEntities != null && nextPageEntities.size() > 0) {
                                    hasNextPage = true;
                                }

                                if (currentPageEntities == null) {
                                    paginationLiveData.setValue(new PaginatedTransactionsHolder(new ArrayList<Transaction>(), false));
                                    entitiesMerger.setValue(currentPageEntities);
                                    return;
                                }

                                List<Transaction> transactions = new ArrayList<>();
                                for (TransactionEntity entity : currentPageEntities) {
                                    if (entity == null) {
                                        continue;
                                    }

                                    transactions.add(entity.parseToDataObject());
                                }

                                paginationLiveData.setValue(new PaginatedTransactionsHolder(transactions, hasNextPage));
                                entitiesMerger.setValue(currentPageEntities);
                            }
                        });
                    }
                });

                return Transformations.switchMap(entitiesMerger, new Function<List<TransactionEntity>, LiveData<PaginatedTransactionsHolder>>() {
                    @Override
                    public LiveData<PaginatedTransactionsHolder> apply(List<TransactionEntity> input) {
                        return paginationLiveData;
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<TransactionListResponse>> createCall() {
                return mApiInterface.getTransactionsOnPage(userId, from, to, page, limit);
            }
        }.asLiveData();
    } */

    public LiveData<Resource<List<Transaction>>> getTransactionsToConfirm() {
        initApiInterface();

        return new NetworkResource<List<Transaction>, TransactionListResponse>(AppExecutors.get()) {
            @Override
            protected List<Transaction> proceedData(@NonNull TransactionListResponse item) {
                return TransactionListResponse.parseToDataObject(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<TransactionListResponse>> createCall() {
                return mApiInterface.getTransactionsToConfirm();
            }
        }.asLiveData();
    }

    public LiveData<ActionResult> changeCategory(Transaction transaction, Category category) {
        initApiInterface();

        final String transactionId = transaction.getId();
        final CategorisationRequest request = new CategorisationRequest(category.getId());

        return new NetworkBoundAction<ResponseBody>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull ResponseBody item) {
                // nothing to do with this
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                return mApiInterface.categoriseTransaction(transactionId, request);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Transaction>> setTransactionStatus(final String accountId, final String transactionId, final String status) {
        initApiInterface();

        return new NetworkResource<Transaction, IncludeExcludeResponse>(AppExecutors.get()) {
            @Override
            protected Transaction proceedData(@NonNull IncludeExcludeResponse item) {
                return TransactionResponse.parseToDataObject(item.getTransaction());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<IncludeExcludeResponse>> createCall() {
                return mApiInterface.setTransactionStatus(accountId, transactionId, status);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Spending>> getSpendingByCategory(final String categoryId, final String date) {
        initApiInterface();

        return new NetworkResource<Spending, SpendingResponse>(AppExecutors.get()) {
            @Override
            protected Spending proceedData(@NonNull SpendingResponse item) {
                return SpendingResponse.parseToDataObject(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SpendingResponse>> createCall() {
                return mApiInterface.getSpendingsByCategory(categoryId, date);
            }
        }.asLiveData();
    }

    public LiveData<Resource<Spending>> getSpendingsByMerchant(final String merchantId, final String date) {
        initApiInterface();

        return new NetworkResource<Spending, SpendingResponse>(AppExecutors.get()) {
            @Override
            protected Spending proceedData(@NonNull SpendingResponse item) {
                return SpendingResponse.parseToDataObject(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<SpendingResponse>> createCall() {
                return mApiInterface.getSpendingsByMerchant(merchantId, date);
            }
        }.asLiveData();
    }

    public LiveData<Resource<ResponseBody>> getTransactionsStatement(final String accountId, final boolean getHTMLPreview, final String from, final String to) {
        initApiInterface();
        final String preparedUrl = mLootSDK.getApiUri() + "/v2/users/accounts/" + accountId + "/transactions?from=" + from + "&to=" + to;

        LiveData<Resource<Void>> resourceLiveData = new NetworkResource<Void, Void>(AppExecutors.get()) {

            @Override
            protected Void proceedData(@NonNull Void item) {
                return item;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Void>> createCall() {
                return mApiInterface.checkStatementAvailability(preparedUrl);
            }
        }.asLiveData();

        return Transformations.switchMap(resourceLiveData, new Function<Resource<Void>, LiveData<Resource<ResponseBody>>>() {
            @Override
            public LiveData<Resource<ResponseBody>> apply(Resource<Void> input) {
                if (input == null) {
                    return null;
                }

                MutableLiveData<Resource<ResponseBody>> resourceLiveData = new MutableLiveData<>();
                if ((!input.isSuccessful() || input.getResponseCode() == 204) && !input.isLoading()) {
                    String error;
                    if ((input.getRawError() != null && !input.getRawError().isEmpty()) || !input.getErrorMessage().equals(ErrorExtractor.UNEXPECTED_ERROR)) {
                        error = input.getRawError();
                    } else {
                        Gson gson = new Gson();
                        ErrorResponse errorResponse = new ErrorResponse();
                        if (input.getResponseCode() == 204) {
                            errorResponse.setErrorString(NO_TRANSACTION_IN_RANGE);
                        } else {
                            errorResponse.setErrorString(CANT_GENERATE_STATEMENT);
                        }
                        error = gson.toJson(errorResponse);
                    }
                    Resource<ResponseBody> resource = Resource.error(error, input.getResponseCode(), null);
                    resourceLiveData.setValue(resource);
                    return resourceLiveData;
                } else if (input.isSuccessful() && !input.isLoading()){
                    return getStatement(getHTMLPreview, accountId, from, to);
                } else {
                    Resource<ResponseBody> resource = Resource.loading(null);
                    resourceLiveData.setValue(resource);
                    return resourceLiveData;
                }
            }
        });
    }

    private LiveData<Resource<ResponseBody>> getStatement(final Boolean getHTMLPreview, final String id, final String from, final String to) {
        initApiInterface();

        return new NetworkResource<ResponseBody, ResponseBody>(AppExecutors.get()) {
            @Override
            protected ResponseBody proceedData(@NonNull ResponseBody item) {
                return item;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<ResponseBody>> createCall() {
                if (getHTMLPreview) {
                    return mApiInterface.getStatementPreview(id, from, to);
                } else {
                    return mApiInterface.getPDFStatement(id, from, to);
                }
            }
        }.asLiveData();
    }

    private void initApiInterface() {
        LootHeader header = mLootSDK.createLootHeader();
        mApiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();
    }

    public static class PaginatedTransactionsHolder {
        private List<Transaction> transactions;
        private boolean hasNextPage;

        public PaginatedTransactionsHolder(List<Transaction> transactions, boolean hasNextPage) {
            this.transactions = transactions;
            this.hasNextPage = hasNextPage;
        }

        public List<Transaction> getTransactions() {
            return transactions;
        }

        public boolean hasNextPage() {
            return hasNextPage;
        }
    }
}
