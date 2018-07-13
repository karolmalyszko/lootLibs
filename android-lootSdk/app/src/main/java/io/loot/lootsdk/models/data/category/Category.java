package io.loot.lootsdk.models.data.category;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Category implements Serializable {

    private String id;
    private String parentId;
    private String name;
    private String priority;
    private String colourHex;
    private CategoryIcon icon;
    private String foundCategory;
    private String slug;

    public Category() {
        id = "";
        parentId = "";
        name = "";
        priority = "";
        colourHex = "";
        icon = new CategoryIcon();
        foundCategory = "";
        slug = "";
    }

    public Category(Category category) {
        id = category.getId();
        parentId = category.getParentId();
        name = category.getName();
        priority = category.getPriority();
        colourHex = category.getColourHex();
        icon = new CategoryIcon(category.getIcon());
        foundCategory = category.getFoundCategory();
        slug = category.getSlug();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null) {
            id = "";
        }
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        if (parentId == null) {
            parentId = "";
        }
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null) {
            name = "";
        }
        this.name = name;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        if (priority == null) {
            priority = "";
        }
        this.priority = priority;
    }

    public String getColourHex() {
        return colourHex;
    }

    public void setColourHex(String colourHex) {
        if (colourHex == null) {
            colourHex = "";
        }
        this.colourHex = colourHex;
    }

    public CategoryIcon getIcon() {
        return icon;
    }

    public void setIcon(CategoryIcon icon) {
        if (icon == null) {
            icon = new CategoryIcon();
        }
        this.icon = icon;
    }

    public String getFoundCategory() {
        return foundCategory;
    }

    public void setFoundCategory(String foundCategory) {  //TODO CHECK TYPE OF FOUND CATEGORY
        if (foundCategory == null) {
            foundCategory = "";
        }
        this.foundCategory = foundCategory;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        if(slug == null) {
            slug = "";
        }
        this.slug = slug;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true; //if both pointing towards same object on heap

        Category a = (Category) obj;
        return this.id.equals(a.id);
    }
}
