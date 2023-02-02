package com.cryoport.skytrax;

import com.amazonaws.services.lambda.runtime.events.CognitoUserPoolPreTokenGenerationEvent;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
public class FunctionRequestHandlerTest extends BaseMongoDataTest{

    private static FunctionRequestHandler handler;

    @BeforeAll
    public static void setupServer() {
        handler = new FunctionRequestHandler();
    }

    @AfterAll
    public static void stopServer() {
        if (handler != null) {
            handler.getApplicationContext().close();
        }
    }

    @Test
    void testHandler() {
        CognitoUserPoolPreTokenGenerationEvent.Request request = CognitoUserPoolPreTokenGenerationEvent.Request
                .builder()
                .withClientMetadata(null)
                .withUserAttributes(null)
                .withGroupConfiguration(null)
                .build();
        CognitoUserPoolPreTokenGenerationEvent.Response response = handler.execute(request);
        assertNotNull(response);

    }
}
