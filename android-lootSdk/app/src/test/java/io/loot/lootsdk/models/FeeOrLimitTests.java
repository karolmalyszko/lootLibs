package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.loot.lootsdk.models.data.userinfo.FeeOrLimit;
import io.loot.lootsdk.models.networking.feesAndLimits.FeeOrLimitResponse;
import io.loot.lootsdk.models.orm.FeeOrLimitEntity;

public class FeeOrLimitTests {

    private FeeOrLimitResponse response;

    @Before
    public void setUp() {
        String title = "title";
        String fee = "fee";
        String description = "desc";
        String createdAt = "createdAt";
        String updatedAt = "updatedAt";

        response = new FeeOrLimitResponse();
        response.setTitle(title);
        response.setFee(fee);
        response.setDescription(description);
        response.setCreatedAt(createdAt);
        response.setUpdatedAt(updatedAt);
    }

    @Test
    public void feeOrLimitResponseToData() throws Exception {
        FeeOrLimit data = FeeOrLimitResponse.parseToDataObject(response);

        Assert.assertEquals(data.getTitle(), response.getTitle());
        Assert.assertEquals(data.getFee(), response.getFee());
        Assert.assertEquals(data.getDescription(), response.getDescription());
        Assert.assertEquals(data.getCreatedAt(), response.getCreatedAt());
        Assert.assertEquals(data.getUpdatedAt(), response.getUpdatedAt());
    }

    @Test
    public void feeOrLimitResponseToEntity() throws Exception {
        FeeOrLimitEntity entity = FeeOrLimitResponse.parseToEntityObject(response);

        Assert.assertEquals(entity.getTitle(), response.getTitle());
        Assert.assertEquals(entity.getFee(), response.getFee());
        Assert.assertEquals(entity.getDescription(), response.getDescription());
        Assert.assertEquals(entity.getCreatedAt(), response.getCreatedAt());
        Assert.assertEquals(entity.getUpdatedAt(), response.getUpdatedAt());
    }

    @Test
    public void feeOrLimitShouldntHaveNulls() throws Exception {
        FeeOrLimit data = new FeeOrLimit();

        Assert.assertNotNull(data.getTitle());
        Assert.assertNotNull(data.getFee());
        Assert.assertNotNull(data.getDescription());
        Assert.assertNotNull(data.getCreatedAt());
        Assert.assertNotNull(data.getUpdatedAt());
    }

    @Test
    public void feeOrLimitShouldntHaveNullsAfterParsing() throws Exception {
        response.setTitle(null);
        response.setFee(null);
        response.setDescription(null);
        response.setCreatedAt(null);
        response.setUpdatedAt(null);

        FeeOrLimit data = FeeOrLimitResponse.parseToDataObject(response);

        Assert.assertNotNull(data.getTitle());
        Assert.assertNotNull(data.getFee());
        Assert.assertNotNull(data.getDescription());
        Assert.assertNotNull(data.getCreatedAt());
        Assert.assertNotNull(data.getUpdatedAt());
    }

}
