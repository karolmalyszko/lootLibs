package io.loot.lootsdk.utils;

import org.junit.Assert;
import org.junit.Test;

import io.loot.lootsdk.DummyDataClass;
import io.loot.lootsdk.models.Resource;

public class ResourceTests {

    @Test
    public void successResourceShouldntBeLoading() throws Exception {
        Resource<DummyDataClass> resource = Resource.success(new DummyDataClass());

        Assert.assertFalse(resource.isLoading());
    }

    @Test
    public void successResourceShouldntBeCached() throws Exception {
        Resource<DummyDataClass> resource = Resource.success(new DummyDataClass());

        Assert.assertFalse(resource.isCached());
    }

    @Test
    public void successResourceShouldBeLive() throws Exception {
        Resource<DummyDataClass> resource = Resource.success(new DummyDataClass());

        Assert.assertTrue(resource.isLive());
    }

    @Test
    public void loadingResourceWithDataIsCached() throws Exception {
        Resource<DummyDataClass> resource = Resource.loading(new DummyDataClass());

        Assert.assertTrue(resource.isCached());
    }

    @Test
    public void loadingResourceWithDataIsLoading() throws Exception {
        Resource<DummyDataClass> resource = Resource.loading(new DummyDataClass());

        Assert.assertTrue(resource.isLoading());
    }

    @Test
    public void errorResourceWithoutDataShouldntBeSuccessful() throws Exception {
        Resource<DummyDataClass> resource = Resource.error("Error", null);

        Assert.assertTrue(!resource.isSuccessful());
    }

    @Test
    public void errorResourceWithoutDataShouldntBeLoading() throws Exception {
        Resource<DummyDataClass> resource = Resource.error("Error", null);

        Assert.assertFalse(resource.isLoading());
    }

    @Test
    public void errorResourceWithDataShouldntBeLoading() throws Exception {
        Resource<DummyDataClass> resource = Resource.error("Error", new DummyDataClass());

        Assert.assertFalse(resource.isLoading());
    }

    @Test
    public void errorResourceWithDataShouldntBeLive() throws Exception {
        Resource<DummyDataClass> resource = Resource.error("Error", new DummyDataClass());

        Assert.assertFalse(resource.isLive());
    }
}
