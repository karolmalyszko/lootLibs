package io.loot.lootsdk.listeners.user;

import java.util.ArrayList;

import io.loot.lootsdk.listeners.GenericFailListener;
import io.loot.lootsdk.models.data.category.Category;

public interface GetCategoriesListener extends GenericFailListener {
    void onGetCachedCategories(ArrayList<Category> categories);
    void onGetCategoriesSuccess(ArrayList<Category> categories);
    void onGetCategoriesError(String error);
}
