package io.loot.lootsdk.models.orm;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

import io.loot.lootsdk.models.data.category.Category;
import io.loot.lootsdk.models.data.category.CategoryIcon;

@Entity(tableName = "Category")
public class CategoryEntity {

    @PrimaryKey
    @NonNull
    private String id = "0";
    private String parentId;
    private String name;
    private String priority;
    private String colourHex;

    private String iconSvgUrl;
    private String iconPngUrl;

    private String foundCategory;

    private String slug;

    public Category parseToDataObject() {
        Category category = new Category();

        category.setId(id);
        category.setParentId(parentId);
        category.setName(name);
        category.setPriority(priority);
        category.setColourHex(colourHex);
        category.setFoundCategory(foundCategory);
        category.setIcon(new CategoryIcon(iconSvgUrl, iconPngUrl));
        category.setSlug(slug);

        return category;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriority() {
        return this.priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getColourHex() {
        return this.colourHex;
    }

    public void setColourHex(String colourHex) {
        this.colourHex = colourHex;
    }

    public String getIconSvgUrl() {
        return this.iconSvgUrl;
    }

    public void setIconSvgUrl(String iconSvgUrl) {
        this.iconSvgUrl = iconSvgUrl;
    }

    public String getIconPngUrl() {
        return this.iconPngUrl;
    }

    public void setIconPngUrl(String iconPngUrl) {
        this.iconPngUrl = iconPngUrl;
    }

    public String getFoundCategory() {
        return this.foundCategory;
    }

    public void setFoundCategory(String foundCategory) {
        this.foundCategory = foundCategory;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public static CategoryEntity parseToEntity(Category category) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(category.getId());
        categoryEntity.setParentId(category.getParentId());
        categoryEntity.setName(category.getName());
        categoryEntity.setPriority(category.getPriority());
        categoryEntity.setColourHex(category.getColourHex());
        categoryEntity.setIconSvgUrl(category.getIcon().getSvgUrl());
        categoryEntity.setIconPngUrl(category.getIcon().getPngUrl());
        categoryEntity.setFoundCategory(category.getFoundCategory());
        categoryEntity.setSlug(category.getSlug());
        return categoryEntity;
    }
}
