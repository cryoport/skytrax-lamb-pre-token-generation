package com.cryoport.skytrax;

import com.amazonaws.services.lambda.runtime.events.CognitoUserPoolPreTokenGenerationEvent;
import com.cryoport.skytrax.services.DefaultClaimService;
import io.micronaut.context.ApplicationContext;
import io.micronaut.function.aws.MicronautRequestHandler;
import jakarta.inject.Inject;

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
        String[] groups = new String[]{};
        if(null != event && null != event.getRequest() && null != event.getRequest().getGroupConfiguration() && null != event.getRequest().getGroupConfiguration().getGroupsToOverride()){
            groups = event.getRequest().getGroupConfiguration().getGroupsToOverride();
        }
        var response =  CognitoUserPoolPreTokenGenerationEvent.Response.builder()
                .withClaimsOverrideDetails(CognitoUserPoolPreTokenGenerationEvent.ClaimsOverrideDetails.builder()
                        .withGroupOverrideDetails(CognitoUserPoolPreTokenGenerationEvent.GroupConfiguration.builder()
                                .withGroupsToOverride(new String[]{""})
                                .withPreferredRole("")
                                .withIamRolesToOverride(new String[]{""})
                                .build())
                        .withClaimsToAddOrOverride(defaultClaimService.getClaims(groups))
                        .withClaimsToSuppress(new String[]{""})
                        .build())
                .build();
        event.setResponse(response);
        return event;
    }

}
