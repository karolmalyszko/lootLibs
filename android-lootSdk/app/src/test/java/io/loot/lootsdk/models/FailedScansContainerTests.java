package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Test;

import io.loot.lootsdk.models.data.signup.FailedScansContainer;

public class FailedScansContainerTests {

    @Test
    public void failedScansAreNullable() throws Exception {
        FailedScansContainer container = new FailedScansContainer();

        Assert.assertNull(container.getBackIdScanBody());
        Assert.assertNull(container.getFaceScanBody());
        Assert.assertNull(container.getIdScanBody());
    }

    @Test
    public void failedScansAreNullableAfterConstruction() throws Exception {
        FailedScansContainer container = new FailedScansContainer(null, null, null);

        Assert.assertNull(container.getBackIdScanBody());
        Assert.assertNull(container.getFaceScanBody());
        Assert.assertNull(container.getIdScanBody());
    }

}
