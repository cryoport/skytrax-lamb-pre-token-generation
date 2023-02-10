package com.cryoport.skytrax.services;

import com.cryoport.skytrax.entity.RolePrivilegeMappingEntity;
import com.cryoport.skytrax.repository.RolePrivilegeMappingRepository;
import io.micronaut.serde.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class DefaultClaimService implements ClaimsService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultClaimService.class);

    private final String ROLE = "role";
    private final String PERMISSIONS = "permissions";

    @Inject
    private ObjectMapper mapper;
    @Inject
    private RolePrivilegeMappingRepository rolePrivilegeMappingRepository;

    @Override
    public Map<String, String> getClaims(String[] groups) {
        try {
            if (groups == null || groups.length == 0) return Collections.emptyMap();
            List<RolePrivilegeMappingEntity> rolePrivilegeMappingEntities = rolePrivilegeMappingRepository.findByRoleIn(groups);
            Map<String, Set<String>> roleMap = rolePrivilegeMappingEntities.stream()
                    .collect(Collectors.toMap(RolePrivilegeMappingEntity::getCustomRoleName, RolePrivilegeMappingEntity::getPrivileges));
            Map<String, String> result = new HashMap<>();
            result.put(PERMISSIONS, mapper.writeValueAsString(roleMap));
            result.put(ROLE, String.join(",", roleMap.keySet()));
            return result;
        } catch (IOException e) {
            LOG.error("Error building permissions ", e);
            return Collections.emptyMap();
        }
    }
}
