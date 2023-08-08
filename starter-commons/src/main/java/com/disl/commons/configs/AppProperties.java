package com.disl.commons.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import static com.disl.commons.constants.CommonConstants.environment;

@Component
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String name;

    private String backendUrl;

    private String backendUrlShort;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackendUrl() {
        return backendUrl;
    }

    public void setBackendUrl(String backendUrl){
        this.backendUrl = backendUrl;
    }

    public String getBackendUrlShort() {
        return backendUrlShort;
    }

    public void setBackendUrlShort(String backendUrlShort) {
        this.backendUrlShort = backendUrlShort;
    }

    public environment getActiveProfile() {
        if (environment.development.name().equals(activeProfile)) {
            return environment.development;
        }

        if (environment.staging.name().equals(activeProfile)) {
            return environment.staging;
        }

        return environment.production;
    }

    public void setActiveProfile(String activeProfile) {
        this.activeProfile = activeProfile;
    }
}
