package com.cryoport.skytrax.config;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotNull;

@ConfigurationProperties("mongo")
public class SkytraxMongoConfiguration implements SkytraxDatasource {

    @NonNull
    @NotNull
    private String host;

    @NonNull
    @NotNull
    private String username;

    @NonNull
    @NotNull
    private String password;

    private Long port;

    @NonNull
    @NotNull
    private String schema;

    @NonNull
    @NotNull
    private String authSource;

    @NonNull
    @NotNull
    private Boolean ssl;

    @Override
    @NonNull
    public String getHost() {
        return host;
    }

    public void setHost(@NonNull String host) {
        this.host = host;
    }

    @Override
    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    @Override
    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @Override
    public Long getPort() {
        return port;
    }

    public void setPort(Long port) {
        this.port = port;
    }

    @Override
    @NonNull
    public String getSchema() {
        return schema;
    }

    public void setSchema(@NonNull String schema) {
        this.schema = schema;
    }

    @Override
    @NonNull
    public String getAuthSource() {
        return authSource;
    }

    public void setAuthSource(@NonNull String authSource) {
        this.authSource = authSource;
    }

    @NonNull
    @Override
    public Boolean isSsl() {
        return ssl;
    }

    public void setSsl(@NonNull Boolean ssl) {
        this.ssl = ssl;
    }
}