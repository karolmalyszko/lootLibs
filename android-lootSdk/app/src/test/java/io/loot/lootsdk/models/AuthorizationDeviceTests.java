package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.authToken.AuthorizedDevice;
import io.loot.lootsdk.models.networking.authToken.AuthDeviceResponse;
import io.loot.lootsdk.models.orm.AuthDeviceEntity;

public class AuthorizationDeviceTests {

    private AuthDeviceResponse response;

    @Before
    public void setUp() {
        response = new AuthDeviceResponse("deviceName", "deviceId");
    }

    @Test
    public void authdeviceResponseToData() throws Exception {
        AuthorizedDevice data = AuthDeviceResponse.parseToDataObject(response);

        Assert.assertEquals(data.getDeviceId(), response.getDeviceId());
        Assert.assertEquals(data.getDeviceName(), response.getDeviceName());
    }

    @Test
    public void authdeviceResponseToEntity() throws Exception {
        AuthDeviceEntity entity = AuthDeviceResponse.parseToEntityObject(response);

        Assert.assertEquals(entity.getDeviceId(), response.getDeviceId());
        Assert.assertEquals(entity.getDeviceName(), response.getDeviceName());
    }

    @Test
    public void authdeviceEntityToData() throws Exception {
        AuthDeviceEntity entity = AuthDeviceResponse.parseToEntityObject(response);
        AuthorizedDevice data = entity.parseToDataObject();

        Assert.assertEquals(entity.getDeviceId(), data.getDeviceId());
        Assert.assertEquals(entity.getDeviceName(), data.getDeviceName());
        Assert.assertNotEquals(data.getDeviceId(), null);
        Assert.assertNotEquals(data.getDeviceName(), null);
    }

    @Test
    public void authdeviceDataShouldntContainsNulls() throws Exception {
        AuthorizedDevice data = new AuthorizedDevice();

        Assert.assertNotEquals(data.getDeviceId(), null);
        Assert.assertNotEquals(data.getDeviceName(), null);
    }

    @Test
    public void authdeviceDataShouldntContainsNullsAfterParsing() throws Exception {
        response.setDeviceId(null);
        response.setDeviceName(null);

        AuthorizedDevice data = AuthDeviceResponse.parseToDataObject(response);
        Assert.assertNotEquals(data.getDeviceId(), null);
        Assert.assertNotEquals(data.getDeviceName(), null);

    }

}
