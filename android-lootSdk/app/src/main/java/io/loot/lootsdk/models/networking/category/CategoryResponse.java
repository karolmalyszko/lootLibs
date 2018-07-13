package io.loot.lootsdk.models.networking.category;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.loot.lootsdk.models.data.category.Category;
import io.loot.lootsdk.models.data.category.CategoryIcon;
import io.loot.lootsdk.models.orm.CategoryEntity;
import lombok.Data;

public @Data
class CategoryResponse implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("parent_id")
    private String parentId;

    @SerializedName("name")
    private String name;

    @SerializedName("priority")
    private String priority;

    @SerializedName("colour")
    private String colourHex;

    @SerializedName("icon")
    private CategoryIconResponse icon;

    @SerializedName("found_category")
    private String foundCategory;

    @SerializedName("slug")
    private String slug;

    public CategoryResponse() {
        icon = new CategoryIconResponse();
    }

    public static CategoryEntity parseToEntityObject(CategoryResponse categoryResponse) {
        if (categoryResponse == null) {
            categoryResponse = new CategoryResponse();
        }
        if (categoryResponse.getIcon() == null) {
            categoryResponse.setIcon(new CategoryIconResponse());
        }
        CategoryEntity category = new CategoryEntity();
        category.setId(categoryResponse.getId());
        category.setParentId(categoryResponse.getParentId());
        category.setName(categoryResponse.getName());
        category.setPriority(categoryResponse.getPriority());
        category.setColourHex(categoryResponse.getColourHex());
        category.setFoundCategory(categoryResponse.getFoundCategory());
        category.setIconSvgUrl(categoryResponse.getIcon().getSvgUrl());
        category.setIconPngUrl(categoryResponse.getIcon().getPngUrl());
        category.setSlug(categoryResponse.getSlug());

        return category;
    }

    public static Category parseToDataObject(CategoryResponse categoryResponse) {
        if (categoryResponse == null) {
            categoryResponse = new CategoryResponse();
        }
        if (categoryResponse.getIcon() == null) {
            categoryResponse.setIcon(new CategoryIconResponse());
        }
        Category category = new Category();
        category.setId(categoryResponse.getId());
        category.setParentId(categoryResponse.getParentId());
        category.setName(categoryResponse.getName());
        category.setPriority(categoryResponse.getPriority());
        category.setColourHex(categoryResponse.getColourHex());
        category.setFoundCategory(categoryResponse.getFoundCategory());
        category.setSlug(categoryResponse.getSlug());
        category.setIcon(CategoryIconResponse.parseToDataObject(categoryResponse.getIcon()));

        return category;
    }
}
