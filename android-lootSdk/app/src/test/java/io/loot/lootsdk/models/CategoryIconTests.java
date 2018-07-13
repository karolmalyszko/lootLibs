package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.category.CategoryIcon;
import io.loot.lootsdk.models.networking.category.CategoryIconResponse;

public class CategoryIconTests {

    private CategoryIconResponse response;

    @Before
    public void setUp() {
        response = new CategoryIconResponse();
        response.setPngUrl("pngUrl");
        response.setSvgUrl("svgUrl");
    }

    @Test
    public void categoryIconResponseToData() throws Exception {
        CategoryIcon data = CategoryIconResponse.parseToDataObject(response);

        Assert.assertEquals(data.getPngUrl(), response.getPngUrl());
        Assert.assertEquals(data.getSvgUrl(), response.getSvgUrl());
    }

    @Test
    public void categoryIconDataShouldntHaveNulls() throws Exception {
        CategoryIcon data = CategoryIconResponse.parseToDataObject(response);

        Assert.assertNotNull(data.getSvgUrl());
        Assert.assertNotNull(data.getPngUrl());
    }

    @Test
    public void categoryIconDataShouldntHaveNullsAfterParse() throws Exception {
        response.setSvgUrl(null);
        response.setPngUrl(null);

        CategoryIcon data = CategoryIconResponse.parseToDataObject(response);
        Assert.assertNotNull(data.getSvgUrl());
        Assert.assertNotNull(data.getPngUrl());

    }

}
