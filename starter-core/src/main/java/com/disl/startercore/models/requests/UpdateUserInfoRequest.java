package com.disl.startercore.models.requests;

import jakarta.validation.constraints.NotBlank;

public class UpdateUserInfoRequest {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
