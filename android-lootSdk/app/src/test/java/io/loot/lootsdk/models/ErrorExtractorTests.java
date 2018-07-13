package io.loot.lootsdk.models;

import org.junit.Assert;
import org.junit.Test;

import io.loot.lootsdk.models.networking.ErrorExtractor;

public class ErrorExtractorTests {

    private String MALFORMED_JSON = "{ error : something.";
    private String PROPER_JSON = "{ error: \'ERROR_MESSAGE\' }";
    private String NOT_PROPER_JSON = "{ somefield : 15, someotherfield : 22 }";
    private String PROPER_JSON_WITH_ADDITIONAL_FIELDS = "{error: \'ERROR_MESSAGE\', anotherField: 15}";
    private String SESSION_EXPIRED_JSON = "{error: \'SESSION_EXPIRED\'}";
    private String NOT_FOUND_JSON = "{error: \'NOT_FOUND\'}";

    private String UNEXPECTED_ERROR = "UNEXPECTED_ERROR";
    private String ERROR_MESSAGE = "ERROR_MESSAGE";

    @Test
    public void malformedJsonShouldReturnUnexpectedError() {
        String error = ErrorExtractor.extract(404, MALFORMED_JSON);

        Assert.assertEquals(error, UNEXPECTED_ERROR);
    }

    @Test
    public void error500ShouldReturnUnexpectedError() {
        String error = ErrorExtractor.extract(500, PROPER_JSON);

        Assert.assertEquals(error, UNEXPECTED_ERROR);
    }

    @Test
    public void properJsonShouldReturnProperError() {
        String error = ErrorExtractor.extract(200, PROPER_JSON);

        Assert.assertEquals(error, ERROR_MESSAGE);
    }

    @Test
    public void notProperJsonShouldReturnUnexpectedError() {
        String error = ErrorExtractor.extract(200, NOT_PROPER_JSON);

        Assert.assertEquals(error, UNEXPECTED_ERROR);
    }

    @Test
    public void jsonWithAdditionalFieldsShouldReturnProperError() {
        String error = ErrorExtractor.extract(200, PROPER_JSON_WITH_ADDITIONAL_FIELDS);

        Assert.assertEquals(error, ERROR_MESSAGE);
    }

    @Test
    public void sessionExpiredShouldReturnFalseWhen500AndSessionExpired() {
        boolean isExpired = ErrorExtractor.isSessionExpired(500, SESSION_EXPIRED_JSON);

        Assert.assertEquals(isExpired, false);
    }

    @Test
    public void sessionExpiredShouldReturnTrueWhenSessionExpired() {
        boolean isExpried = ErrorExtractor.isSessionExpired(200, SESSION_EXPIRED_JSON);

        Assert.assertEquals(isExpried, true);
    }

    @Test
    public void sessionExpiredSshouldReturnFalseWhenNotExpired() {
        boolean isExpired = ErrorExtractor.isSessionExpired(202, PROPER_JSON);

        Assert.assertEquals(isExpired, false);
    }

    @Test
    public void sessionExpiredShouldReturnFalseWhenMalformedJson() {
        boolean isExpired = ErrorExtractor.isSessionExpired(202, MALFORMED_JSON);

        Assert.assertEquals(isExpired, false);
    }

    @Test
    public void notFoundShouldReturnTrueWhenNotFoundError() {
        boolean isNotFound = ErrorExtractor.isNotFound(200, NOT_FOUND_JSON);

        Assert.assertEquals(isNotFound, true);
    }

    @Test
    public void notFoundShouldReturnTrueWhenOtherError() {
        boolean isNotFound = ErrorExtractor.isNotFound(200, PROPER_JSON);

        Assert.assertEquals(isNotFound, false);
    }

}
