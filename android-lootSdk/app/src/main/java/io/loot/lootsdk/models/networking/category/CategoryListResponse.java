package io.loot.lootsdk.models.networking.category;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import io.loot.lootsdk.models.data.category.Category;
import io.loot.lootsdk.models.orm.CategoryEntity;
import lombok.Data;
@Data
public class CategoryListResponse implements Serializable {
    @SerializedName("categories")
    ArrayList<CategoryResponse> categories;

    public static ArrayList<CategoryEntity> parseToEntityObject(CategoryListResponse categoryListResponse) {
        if (categoryListResponse == null) {
            categoryListResponse = new CategoryListResponse();
        }
        if (categoryListResponse.getCategories() == null) {
            categoryListResponse.setCategories(new ArrayList<CategoryResponse>());
        }
        ArrayList<CategoryEntity> categoryEntities = new ArrayList<CategoryEntity>();
        for (CategoryResponse categoryResponse : categoryListResponse.getCategories()) {
            categoryEntities.add(CategoryResponse.parseToEntityObject(categoryResponse));
        }
        return categoryEntities;
    }

    public static ArrayList<Category>  parseToDataObject(CategoryListResponse categoryListResponse) {
        if (categoryListResponse == null) {
            categoryListResponse = new CategoryListResponse();
        }
        if (categoryListResponse.getCategories() == null) {
            categoryListResponse.setCategories(new ArrayList<CategoryResponse>());
        }
        ArrayList<Category> categories = new ArrayList<Category>();
        for (CategoryResponse categoryResponse : categoryListResponse.getCategories()) {
            categories.add(CategoryResponse.parseToDataObject(categoryResponse));
        }
        return categories;
    }

    public static ArrayList<String>  getIdArray(CategoryListResponse categoryListResponse) {
        if (categoryListResponse == null) {
            categoryListResponse = new CategoryListResponse();
        }
        if (categoryListResponse.getCategories() == null) {
            categoryListResponse.setCategories(new ArrayList<CategoryResponse>());
        }
        ArrayList<String> arrayId = new ArrayList<String>();
        for (CategoryResponse categoryResponse : categoryListResponse.getCategories()) {
            arrayId.add(categoryResponse.getId());
        }
        return arrayId;
    }
}
