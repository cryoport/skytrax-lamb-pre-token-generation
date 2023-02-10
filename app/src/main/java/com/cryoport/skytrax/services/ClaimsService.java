package com.cryoport.skytrax.services;

import java.util.Map;

public interface ClaimsService {
    Map<String,String> getClaims(String[] groups);
}
