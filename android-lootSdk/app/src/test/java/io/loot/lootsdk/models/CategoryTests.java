package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.category.Category;
import io.loot.lootsdk.models.networking.category.CategoryIconResponse;
import io.loot.lootsdk.models.networking.category.CategoryResponse;
import io.loot.lootsdk.models.orm.CategoryEntity;

public class CategoryTests {

    private CategoryResponse response;

    @Before
    public void setUp() {
        String id = "id";
        String parentId = "parentId";
        String name = "name";
        String priority = "priority";
        String colourHex = "#000000";
        CategoryIconResponse icon = new CategoryIconResponse();
        String foundCategory = "foundCategory";

        response = new CategoryResponse();
        response.setId(id);
        response.setParentId(parentId);
        response.setName(name);
        response.setPriority(priority);
        response.setColourHex(colourHex);
        response.setIcon(icon);
        response.setFoundCategory(foundCategory);
    }

    @Test
    public void categoryResponseToDataTest() throws Exception {
        Category data = CategoryResponse.parseToDataObject(response);

        Assert.assertEquals(data.getId(), response.getId());
        Assert.assertEquals(data.getParentId(), response.getParentId());
        Assert.assertEquals(data.getName(), response.getName());
        Assert.assertEquals(data.getPriority(), response.getPriority());
        Assert.assertEquals(data.getColourHex(), response.getColourHex());
        Assert.assertEquals(data.getFoundCategory(), response.getFoundCategory());
    }

    @Test
    public void categoryResponseToEntityTest() throws Exception {
        CategoryEntity entity = CategoryResponse.parseToEntityObject(response);

        Assert.assertEquals(entity.getId(), response.getId());
        Assert.assertEquals(entity.getParentId(), response.getParentId());
        Assert.assertEquals(entity.getName(), response.getName());
        Assert.assertEquals(entity.getPriority(), response.getPriority());
        Assert.assertEquals(entity.getColourHex(), response.getColourHex());
        Assert.assertEquals(entity.getFoundCategory(), response.getFoundCategory());
    }

    @Test
    public void categoryEntityToDataTest() throws Exception {
        CategoryEntity entity = CategoryResponse.parseToEntityObject(response);
        Category data = entity.parseToDataObject();

        Assert.assertEquals(entity.getId(), data.getId());
        Assert.assertEquals(entity.getParentId(), data.getParentId());
        Assert.assertEquals(entity.getName(), data.getName());
        Assert.assertEquals(entity.getPriority(), data.getPriority());
        Assert.assertEquals(entity.getColourHex(), data.getColourHex());
        Assert.assertEquals(entity.getFoundCategory(), data.getFoundCategory());
    }

    @Test
    public void categoryDataShouldntContainsNullValues() throws Exception {
        Category data = new Category();

        Assert.assertNotNull(data.getId());
        Assert.assertNotNull(data.getParentId());
        Assert.assertNotNull(data.getName());
        Assert.assertNotNull(data.getPriority());
        Assert.assertNotNull(data.getColourHex());
        Assert.assertNotNull(data.getIcon());
        Assert.assertNotNull(data.getFoundCategory());
    }

    @Test
    public void categoryDataShouldntHaveNullsAfterParsing() throws Exception {
        response.setId(null);
        response.setParentId(null);
        response.setName(null);
        response.setPriority(null);
        response.setColourHex(null);
        response.setIcon(null);
        response.setFoundCategory(null);

        Category data = CategoryResponse.parseToDataObject(response);
        Assert.assertNotNull(data.getId());
        Assert.assertNotNull(data.getParentId());
        Assert.assertNotNull(data.getName());
        Assert.assertNotNull(data.getPriority());
        Assert.assertNotNull(data.getColourHex());
        Assert.assertNotNull(data.getIcon());
        Assert.assertNotNull(data.getFoundCategory());
    }

}
