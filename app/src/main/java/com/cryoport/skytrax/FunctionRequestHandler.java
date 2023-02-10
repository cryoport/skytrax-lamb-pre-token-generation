package com.cryoport.skytrax;

import com.amazonaws.services.lambda.runtime.events.CognitoUserPoolPreTokenGenerationEvent;
import com.cryoport.skytrax.services.DefaultClaimService;
import io.micronaut.context.ApplicationContext;
import io.micronaut.function.aws.MicronautRequestHandler;
import jakarta.inject.Inject;

import java.util.Objects;
import java.util.function.Predicate;

public class FunctionRequestHandler extends MicronautRequestHandler<CognitoUserPoolPreTokenGenerationEvent, CognitoUserPoolPreTokenGenerationEvent> {

    public FunctionRequestHandler() {
    }

    public FunctionRequestHandler(ApplicationContext applicationContext) {
        super(applicationContext);
    }

    @Inject
    DefaultClaimService defaultClaimService;

    @Override
    public CognitoUserPoolPreTokenGenerationEvent execute(CognitoUserPoolPreTokenGenerationEvent event) {
        if (hasGroupsToOverride.test(event)) {
            String[] groups = event.getRequest().getGroupConfiguration().getGroupsToOverride();
            var response = CognitoUserPoolPreTokenGenerationEvent.Response.builder()
                    .withClaimsOverrideDetails(CognitoUserPoolPreTokenGenerationEvent.ClaimsOverrideDetails.builder()
                            .withGroupOverrideDetails(CognitoUserPoolPreTokenGenerationEvent.GroupConfiguration.builder()
                                    .build())
                            .withClaimsToAddOrOverride(defaultClaimService.getClaims(groups))
                            .build())
                    .build();
            event.setResponse(response);
        }
        return event;
    }

    Predicate<CognitoUserPoolPreTokenGenerationEvent> hasGroupsToOverride =
            event -> event != null &&
                     event.getRequest() != null &&
                     event.getRequest().getGroupConfiguration() != null &&
                     event.getRequest().getGroupConfiguration().getGroupsToOverride() != null;
}
