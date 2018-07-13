package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.userinfo.Provider;
import io.loot.lootsdk.models.networking.user.userinfo.ProviderResponse;
import io.loot.lootsdk.models.orm.ProviderEntity;

public class ProviderTests {

    private ProviderResponse response;

    @Before
    public void setUp() {
        String id = "id";
        String name = "name";
        String priority = "priority";
        String createdAt = "createdAt";
        String updatedAt = "updatedAt";
        String url = "url";
        boolean isDefault = true;

        response = new ProviderResponse();
        response.setId(id);
        response.setName(name);
        response.setPriority(priority);
        response.setCreatedAt(createdAt);
        response.setUpdatedAt(updatedAt);
        response.setUrl(url);
        response.setDefault(isDefault);
    }

    @Test
    public void providerResponseToData() throws Exception {
        Provider data = ProviderResponse.parseToDataObject(response);

        Assert.assertEquals(data.getId(), response.getId());
        Assert.assertEquals(data.getName(), response.getName());
        Assert.assertEquals(data.getPriority(), response.getPriority());
        Assert.assertEquals(data.getCreatedAt(), response.getCreatedAt());
        Assert.assertEquals(data.getUpdatedAt(), response.getUpdatedAt());
        Assert.assertEquals(data.getUrl(), response.getUrl());
        Assert.assertEquals(data.isDefault(), response.isDefault());
    }

    @Test
    public void providerResponseToEntity() throws Exception {
        ProviderEntity entity = ProviderResponse.parseToEntityObject(response);

        Assert.assertEquals(entity.getId(), response.getId());
        Assert.assertEquals(entity.getName(), response.getName());
        Assert.assertEquals(entity.getPriority(), response.getPriority());
        Assert.assertEquals(entity.getCreatedAt(), response.getCreatedAt());
        Assert.assertEquals(entity.getUpdatedAt(), response.getUpdatedAt());
        Assert.assertEquals(entity.getUrl(), response.getUrl());
        Assert.assertEquals(entity.getIsDefault(), response.isDefault());
    }

    @Test
    public void providerDataShouldntHaveNulls() throws Exception {
        Provider data = new Provider();

        Assert.assertNotNull(data.getId());
        Assert.assertNotNull(data.getName());
        Assert.assertNotNull(data.getPriority());
        Assert.assertNotNull(data.getCreatedAt());
        Assert.assertNotNull(data.getUpdatedAt());
        Assert.assertNotNull(data.getUrl());
        Assert.assertNotNull(data.isDefault());
    }

    @Test
    public void providerDataShouldntHaveNullsAfterParsing() throws Exception {
        response.setId(null);
        response.setName(null);
        response.setPriority(null);
        response.setCreatedAt(null);
        response.setUpdatedAt(null);
        response.setUrl(null);
        response.setDefault(false);

        Provider data = ProviderResponse.parseToDataObject(response);
        Assert.assertNotNull(data.getId());
        Assert.assertNotNull(data.getName());
        Assert.assertNotNull(data.getPriority());
        Assert.assertNotNull(data.getCreatedAt());
        Assert.assertNotNull(data.getUpdatedAt());
        Assert.assertNotNull(data.getUrl());
        Assert.assertNotNull(data.isDefault());
    }

}
