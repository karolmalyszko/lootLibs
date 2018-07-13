package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.userinfo.Address;
import io.loot.lootsdk.models.networking.user.userinfo.AddressResponse;
import io.loot.lootsdk.models.orm.AddressEntity;

public class AddressTests {

    private AddressResponse response;

    @Before
    public void setUp() {
        String postTown = "postTown";
        String postcode = "postCode";
        String county = "county";
        String country = "country";
        String addressLine1 = "line1";
        String addressLine2 = "line2";
        String addressLine3 = "line3";
        String summaryLine = "summaryline";
        String premise = "premise";
        String createdAt = "created_at";

        response = new AddressResponse();
        response.setPostTown(postTown);
        response.setPostcode(postcode);
        response.setCounty(county);
        response.setCountry(country);
        response.setAddressLine1(addressLine1);
        response.setAddressLine2(addressLine2);
        response.setAddressLine3(addressLine3);
        response.setSummaryLine(summaryLine);
        response.setPremise(premise);
        response.setCreatedAt(createdAt);
    }

    @Test
    public void addressResponseToData() throws Exception {
        Address data = AddressResponse.parseToDataObject(response);

        Assert.assertEquals(data.getPostTown(), response.getPostTown());
        Assert.assertEquals(data.getPostcode(), response.getPostcode());
        Assert.assertEquals(data.getCounty(), response.getCounty());
        Assert.assertEquals(data.getCountry(), response.getCountry());
        Assert.assertEquals(data.getAddressLine1(), response.getAddressLine1());
        Assert.assertEquals(data.getAddressLine2(), response.getAddressLine2());
        Assert.assertEquals(data.getAddressLine3(), response.getAddressLine3());
        Assert.assertEquals(data.getSummaryLine(), response.getSummaryLine());
        Assert.assertEquals(data.getPremise(), response.getPremise());
        Assert.assertEquals(data.getCreatedAt(), response.getCreatedAt());
    }


    @Test
    public void addressResponseToEntity() throws Exception {
        AddressEntity entity = AddressResponse.parseToEntityObject(response);

        Assert.assertEquals(entity.getPostTown(), response.getPostTown());
        Assert.assertEquals(entity.getPostcode(), response.getPostcode());
        Assert.assertEquals(entity.getCounty(), response.getCounty());
        Assert.assertEquals(entity.getCountry(), response.getCountry());
        Assert.assertEquals(entity.getAddressLine1(), response.getAddressLine1());
        Assert.assertEquals(entity.getAddressLine2(), response.getAddressLine2());
        Assert.assertEquals(entity.getAddressLine3(), response.getAddressLine3());
        Assert.assertEquals(entity.getSummaryLine(), response.getSummaryLine());
        Assert.assertEquals(entity.getPremise(), response.getPremise());
        Assert.assertEquals(entity.getCreatedAt(), response.getCreatedAt());
    }

    @Test
    public void addressDataToResponse() throws Exception {
        Address data = AddressResponse.parseToDataObject(response);
        AddressResponse addressResponse = AddressResponse.parseToResponseObject(data);

        Assert.assertEquals(data.getPostTown(), addressResponse.getPostTown());
        Assert.assertEquals(data.getPostcode(), addressResponse.getPostcode());
        Assert.assertEquals(data.getCounty(), addressResponse.getCounty());
        Assert.assertEquals(data.getCountry(), addressResponse.getCountry());
        Assert.assertEquals(data.getAddressLine1(), addressResponse.getAddressLine1());
        Assert.assertEquals(data.getAddressLine2(), addressResponse.getAddressLine2());
        Assert.assertEquals(data.getAddressLine3(), addressResponse.getAddressLine3());
        Assert.assertEquals(data.getSummaryLine(), addressResponse.getSummaryLine());
        Assert.assertEquals(data.getPremise(), addressResponse.getPremise());
        Assert.assertEquals(data.getCreatedAt(), addressResponse.getCreatedAt());
    }

    @Test
    public void addressShouldntHaveNulls() throws Exception {
        Address data = new Address();

        Assert.assertNotNull(data.getPostTown());
        Assert.assertNotNull(data.getPostcode());
        Assert.assertNotNull(data.getCounty());
        Assert.assertNotNull(data.getCountry());
        Assert.assertNotNull(data.getAddressLine1());
        Assert.assertNotNull(data.getAddressLine2());
        Assert.assertNotNull(data.getAddressLine3());
        Assert.assertNotNull(data.getSummaryLine());
        Assert.assertNotNull(data.getPremise());
        Assert.assertNotNull(data.getCreatedAt());
    }

    @Test
    public void addressShouldntHaveNullsAfterParsing() throws Exception {
        response.setPostTown(null);
        response.setPostcode(null);
        response.setCounty(null);
        response.setCountry(null);
        response.setAddressLine1(null);
        response.setAddressLine2(null);
        response.setAddressLine3(null);
        response.setSummaryLine(null);
        response.setPremise(null);
        response.setCreatedAt(null);

        Address data = AddressResponse.parseToDataObject(response);

        Assert.assertNotNull(data.getPostTown());
        Assert.assertNotNull(data.getPostcode());
        Assert.assertNotNull(data.getCounty());
        Assert.assertNotNull(data.getCountry());
        Assert.assertNotNull(data.getAddressLine1());
        Assert.assertNotNull(data.getAddressLine2());
        Assert.assertNotNull(data.getAddressLine3());
        Assert.assertNotNull(data.getSummaryLine());
        Assert.assertNotNull(data.getPremise());
        Assert.assertNotNull(data.getCreatedAt());
    }

}
