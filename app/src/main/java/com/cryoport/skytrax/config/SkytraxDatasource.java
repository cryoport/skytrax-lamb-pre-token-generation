package com.cryoport.skytrax.config;

import io.micronaut.core.annotation.NonNull;

public interface SkytraxDatasource {

    @NonNull
    String getHost();
    @NonNull
    String getUsername();
    @NonNull
    String getPassword();
    @NonNull
    Long getPort();
    @NonNull
    String getSchema();
    @NonNull
    String getAuthSource();
    @NonNull
    Boolean isSsl();




}
