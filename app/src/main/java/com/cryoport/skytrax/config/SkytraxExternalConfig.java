package com.cryoport.skytrax.config;

import com.amazonaws.services.lambda.runtime.events.CognitoUserPoolEvent;
import com.amazonaws.services.lambda.runtime.events.CognitoUserPoolPreTokenGenerationEvent;
import io.micronaut.core.annotation.Introspected;

@Introspected(
        classes = {
                CognitoUserPoolEvent.class,
                CognitoUserPoolEvent.CallerContext.class,
                CognitoUserPoolPreTokenGenerationEvent.class,
                CognitoUserPoolPreTokenGenerationEvent.Request.class,
                CognitoUserPoolPreTokenGenerationEvent.Response.class,
                CognitoUserPoolPreTokenGenerationEvent.GroupConfiguration.class,
                CognitoUserPoolPreTokenGenerationEvent.ClaimsOverrideDetails.class,
        })
public class SkytraxExternalConfig {
}
