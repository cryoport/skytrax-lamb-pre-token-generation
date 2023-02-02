package com.cryoport.skytrax;

import com.amazonaws.services.lambda.runtime.events.CognitoUserPoolPreTokenGenerationEvent;
import io.micronaut.function.aws.MicronautRequestHandler;

import java.util.Map;

public class FunctionRequestHandler extends MicronautRequestHandler<CognitoUserPoolPreTokenGenerationEvent.Request, CognitoUserPoolPreTokenGenerationEvent.Response> {

    @Override
    public CognitoUserPoolPreTokenGenerationEvent.Response execute(CognitoUserPoolPreTokenGenerationEvent.Request input) {
        return CognitoUserPoolPreTokenGenerationEvent.Response.builder()
                .withClaimsOverrideDetails(CognitoUserPoolPreTokenGenerationEvent.ClaimsOverrideDetails.builder()
                        .withGroupOverrideDetails(CognitoUserPoolPreTokenGenerationEvent.GroupConfiguration.builder()
                                .withGroupsToOverride(new String[]{""})
                                .withPreferredRole("")
                                .withIamRolesToOverride(new String[]{""})
                                .build())
                        .withClaimsToAddOrOverride(Map.of("claimKey","claimValue"))
                        .withClaimsToSuppress(new String[]{""})
                        .build())
                .build();
    }

}
