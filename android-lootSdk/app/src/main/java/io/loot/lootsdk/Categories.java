package io.loot.lootsdk;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import io.loot.lootsdk.database.daos.CategoryDao;
import io.loot.lootsdk.exceptions.NullPointerListenerException;
import io.loot.lootsdk.listeners.user.GetCategoriesListener;
import io.loot.lootsdk.models.Resource;
import io.loot.lootsdk.models.data.category.Category;
import io.loot.lootsdk.models.data.transactions.Transaction;
import io.loot.lootsdk.models.networking.category.CategoryListResponse;
import io.loot.lootsdk.models.networking.category.CategoryResponse;
import io.loot.lootsdk.models.orm.CategoryEntity;
import io.loot.lootsdk.networking.ApiResponse;
import io.loot.lootsdk.networking.LootApiInterface;
import io.loot.lootsdk.networking.interceptors.InterceptorType;
import io.loot.lootsdk.networking.interceptors.models.LootHeader;
import io.loot.lootsdk.utils.AppExecutors;

public class Categories {

    private LootSDK mLootSDK;
    private CategoryDao mCategoryDao;

    Categories(LootSDK lootSDK) {
        mLootSDK = lootSDK;
        mCategoryDao = lootSDK.getDatabase().categoryDao();
    }

    public LiveData<Resource<List<Category>>> getCategories() {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkBoundResource<List<Category>, CategoryListResponse>(AppExecutors.get()) {
            @Override
            protected void saveCallResult(@NonNull CategoryListResponse item) {
                for (CategoryResponse response : item.getCategories()) {
                    mCategoryDao.insert(CategoryResponse.parseToEntityObject(response));
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Category> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Category>> loadFromDb() {
                return Transformations.map(mCategoryDao.loadAll(), new Function<List<CategoryEntity>, List<Category>>() {
                    @Override
                    public List<Category> apply(List<CategoryEntity> input) {
                        if (input == null) {
                            return null;
                        }

                        List<Category> categories = new ArrayList<>();
                        for (CategoryEntity entity : input) {
                            if (entity == null) {
                                continue;
                            }

                            categories.add(entity.parseToDataObject());
                        }

                        return categories;
                    }
                });
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CategoryListResponse>> createCall() {
                return apiInterface.getCategories();
            }
        }.asLiveData();
    }

    //TODO: Some kind of caching mechanism would be good here
    public LiveData<Resource<List<Category>>> getAvailableCategories(final Transaction transaction) {
        LootHeader header = mLootSDK.createLootHeader();
        final LootApiInterface apiInterface = mLootSDK.createRestClient(InterceptorType.SESSION_TOKEN, header).getApiService();

        return new NetworkResource<List<Category>, CategoryListResponse>(AppExecutors.get()) {
            @Override
            protected List<Category> proceedData(@NonNull CategoryListResponse item) {
                return CategoryListResponse.parseToDataObject(item);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<CategoryListResponse>> createCall() {
                return apiInterface.getAvailableCategories(transaction.getId());
            }
        }.asLiveData();
    }
}
