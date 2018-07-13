package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.userinfo.OnBoardingUserData;
import io.loot.lootsdk.models.entities.OnBoardingUserDataRoomEntity;
import io.loot.lootsdk.models.networking.signup.OnBoardingUserDataResponse;
import io.loot.lootsdk.models.orm.OnBoardingUserDataEntity;

public class OnBoardingUserDataTests {

    private OnBoardingUserDataResponse response;

    @Before
    public void setUp() {
        String phoneNumber = "samplePhoneNumber";
        String email = "email@com.pl";
        String token = "SampleToken";
        String lastFinishedStep = "step";
        String intercomHash = "intercomHash";

        response = new OnBoardingUserDataResponse();
        response.setPhoneNumber(phoneNumber);
        response.setToken(token);
        response.setIntercomHash(intercomHash);
        response.setEmail(email);
        response.setLastFinishedStep(lastFinishedStep);
    }

    @Test
    public void onboardingDataResponseToDataTest() throws Exception {
        OnBoardingUserData data = OnBoardingUserDataResponse.parseToDataObject(response);

        Assert.assertEquals(response.getEmail(), data.getEmail());
        Assert.assertEquals(response.getPhoneNumber(), data.getPhoneNumber());
        Assert.assertEquals(response.getIntercomHash(), data.getIntercomHash());
        Assert.assertEquals(response.getToken(), data.getToken());
        Assert.assertEquals(response.getLastFinishedStep(), data.getLastFinishedStep());
    }


    @Test
    public void onboardingDataResponseToEntityTest() throws Exception {
        OnBoardingUserDataRoomEntity entity = OnBoardingUserDataEntity.parseToEntity(OnBoardingUserDataResponse.parseToDataObject(response));

        Assert.assertEquals(response.getEmail(), entity.getEmail());
        Assert.assertEquals(response.getPhoneNumber(), entity.getPhoneNumber());
        Assert.assertEquals(response.getIntercomHash(), entity.getIntercomHash());
        Assert.assertEquals(response.getToken(), entity.getToken());
        Assert.assertEquals(response.getLastFinishedStep(), entity.getLastFinishedStep());
    }

    @Test
    public void onboardingUserDataEntityToDataTest() throws Exception {
        OnBoardingUserDataRoomEntity entity = OnBoardingUserDataEntity.parseToEntity(OnBoardingUserDataResponse.parseToDataObject(response));
        OnBoardingUserData data = entity.parseToDataObject();

        Assert.assertEquals(data.getEmail(), entity.getEmail());
        Assert.assertEquals(data.getPhoneNumber(), entity.getPhoneNumber());
        Assert.assertEquals(data.getIntercomHash(), entity.getIntercomHash());
        Assert.assertEquals(data.getToken(), entity.getToken());
        Assert.assertEquals(data.getLastFinishedStep(), entity.getLastFinishedStep());

        Assert.assertNotEquals(data.getEmail(), null);
        Assert.assertNotEquals(data.getPhoneNumber(), null);
        Assert.assertNotEquals(data.getIntercomHash(), null);
        Assert.assertNotEquals(data.getToken(), null);
        Assert.assertNotEquals(data.getLastFinishedStep(), null);
        Assert.assertNotEquals(data.getKyc(), null);
        Assert.assertNotEquals(data.getPersonalData(), null);
        Assert.assertNotEquals(data.getPublicId(), null);
    }

    @Test
    public void onboadingDataShouldntContainsNullValues() throws Exception {
        OnBoardingUserData data = new OnBoardingUserData();

        Assert.assertNotEquals(data.getEmail(), null);
        Assert.assertNotEquals(data.getPhoneNumber(), null);
        Assert.assertNotEquals(data.getIntercomHash(), null);
        Assert.assertNotEquals(data.getToken(), null);
        Assert.assertNotEquals(data.getLastFinishedStep(), null);
        Assert.assertNotEquals(data.getKyc(), null);
        Assert.assertNotEquals(data.getPersonalData(), null);
        Assert.assertNotEquals(data.getPublicId(), null);
    }

    @Test
    public void onboardingDataShouldntContainsNullAfterParsing() throws Exception {
        response.setEmail(null);
        response.setPhoneNumber(null);
        response.setIntercomHash(null);
        response.setToken(null);
        response.setLastFinishedStep(null);
        response.setKyc(null);
        response.setPersonalData(null);

        OnBoardingUserData data = OnBoardingUserDataResponse.parseToDataObject(response);

        Assert.assertNotEquals(data.getEmail(), null);
        Assert.assertNotEquals(data.getPhoneNumber(), null);
        Assert.assertNotEquals(data.getIntercomHash(), null);
        Assert.assertNotEquals(data.getToken(), null);
        Assert.assertNotEquals(data.getLastFinishedStep(), null);
        Assert.assertNotEquals(data.getKyc(), null);
        Assert.assertNotEquals(data.getPersonalData(), null);
        Assert.assertNotEquals(data.getPublicId(), null);
    }

}
