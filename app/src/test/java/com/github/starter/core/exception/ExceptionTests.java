package com.github.starter.core.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Exception Tests")
public class ExceptionTests {

    @Test
    @DisplayName("Bad Request Exception Test")
    public void testBadRequestException() {
        ApiException badRequest = new BadRequest();
        Assertions.assertEquals(400, badRequest.getStatus());
        Assertions.assertNotNull(badRequest.getCode());
        Assertions.assertNotNull(badRequest.getSummary());
    }

    @Test
    @DisplayName("Bad Request Exception Test")
    public void testInternalServerError() {
        ApiException internalServerError = new InternalServerError();
        Assertions.assertEquals(500, internalServerError.getStatus());
        Assertions.assertNotNull(internalServerError.getCode());
        Assertions.assertNotNull(internalServerError.getSummary());
    }
}
