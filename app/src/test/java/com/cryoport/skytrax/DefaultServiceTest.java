package com.cryoport.skytrax;

import com.cryoport.skytrax.entity.RolePrivilegeMappingEntity;
import com.cryoport.skytrax.repository.RolePrivilegeMappingRepository;
import com.cryoport.skytrax.services.ClaimsService;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DefaultServiceTest extends BaseMongoDataTest {
    @Inject
    RolePrivilegeMappingRepository repository;
    @Inject
    ClaimsService service;

    private final String PERMISSIONS = "permissions";

    private final String ROLE = "role";

    @Test
    void VerifyGetClaims(){
        createMappings();
        Map<String,String> claims = service.getClaims(new String[]{"ADMINISTRATOR","ENGINEER"});
        assertFalse(claims.isEmpty());
        assertNotNull(claims.get(PERMISSIONS));
        assertNotNull(claims.get(ROLE));
        assertEquals("Engineer,Admin",claims.get(ROLE));
    }
    @Test
    void VerifyGetClaimsNoData(){
        createMappings();
        Map<String,String> claims = service.getClaims(new String[]{"READ-ONLY"});
        assertEquals("{}",claims.get(PERMISSIONS));
        assertEquals("",claims.get(ROLE));
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
