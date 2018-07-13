package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Test;

import io.loot.lootsdk.models.data.userinfo.Address;
import io.loot.lootsdk.models.data.userinfo.UpdateUserDetailsRequest;

public class UpdateUserDetailsTest {

    @Test
    public void updateRequestShouldntHaveNulls() throws Exception {
        UpdateUserDetailsRequest request = new UpdateUserDetailsRequest();

        Assert.assertNotNull(request.getAddress());
        Assert.assertNotNull(request.getPreferredName());
        Assert.assertNotNull(request.getTitle());
    }

    @Test
    public void updateRequestShouldntHaveNullsAfterConstruct() throws Exception {
        UpdateUserDetailsRequest request = new UpdateUserDetailsRequest(null, null, null);

        Assert.assertNotNull(request.getAddress());
        Assert.assertNotNull(request.getPreferredName());
        Assert.assertNotNull(request.getTitle());
    }

    @Test
    public void updateRequestShouldntHaveNullsAfterSetting() throws Exception {
        UpdateUserDetailsRequest request = new UpdateUserDetailsRequest("prefName", "title", new Address());

        request.setPreferredName(null);
        request.setTitle(null);
        request.setAddress(null);

        Assert.assertNotNull(request.getAddress());
        Assert.assertNotNull(request.getPreferredName());
        Assert.assertNotNull(request.getTitle());
    }

}
