package com.cryoport.skytrax;

import com.amazonaws.services.lambda.runtime.events.CognitoUserPoolPreTokenGenerationEvent;
import com.cryoport.skytrax.entity.RolePrivilegeMappingEntity;
import com.cryoport.skytrax.repository.RolePrivilegeMappingRepository;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
class FunctionRequestHandlerTest extends BaseMongoDataTest{

    @Inject
    RolePrivilegeMappingRepository repository;



    private static FunctionRequestHandler handler;

    @BeforeAll
    void setupServer() {
        handler = new FunctionRequestHandler(application.getApplicationContext());
    }

    @AfterAll
    void stopServer() {
        if (handler != null) {
            handler.getApplicationContext().close();
        }
    }

    @Test
    void testHandler() {
        var request = CognitoUserPoolPreTokenGenerationEvent.Request
                .builder()
                .withClientMetadata(null)
                .withUserAttributes(null)
                .withGroupConfiguration(null)
                .build();
        var event = CognitoUserPoolPreTokenGenerationEvent.builder()
                .withRequest(request)
                .build();
        var response = handler.execute(event);
        assertNotNull(response);
    }

    @Test
    void testHandlerWithData() {
        createMappings();
        var request = CognitoUserPoolPreTokenGenerationEvent.Request
                .builder()
                .withClientMetadata(null)
                .withUserAttributes(null)
                .withGroupConfiguration(CognitoUserPoolPreTokenGenerationEvent.GroupConfiguration.builder()
                        .withGroupsToOverride(new String[]{"ADMINISTRATOR"}).build())
                .build();
        var event = CognitoUserPoolPreTokenGenerationEvent.builder()
                .withRequest(request)
                .build();
        var response = handler.execute(event);
        assertNotNull(response);
        assertNotNull(response.getResponse().getClaimsOverrideDetails());
        assertEquals("Admin",response.getResponse().getClaimsOverrideDetails().getClaimsToAddOrOverride().get("role"));
    }
    private void createMappings() {
        RolePrivilegeMappingEntity entity = new RolePrivilegeMappingEntity();
        entity.setId(UUID.randomUUID().toString());
        entity.setRole("ADMINISTRATOR");
        entity.setCustomRoleName("Admin");
        entity.setPrivileges(new HashSet<>(Arrays.asList("Device.add","Device.view")));
        repository.save(entity);

        RolePrivilegeMappingEntity entity1 = new RolePrivilegeMappingEntity();
        entity1.setId(UUID.randomUUID().toString());
        entity1.setRole("ENGINEER");
        entity1.setCustomRoleName("Engineer");
        entity1.setPrivileges(new HashSet<>(Arrays.asList("Device.add","CONFIGURATION.SET_INTERVALS")));
        repository.save(entity1);
    }
}
