package com.disl.localization.language.payloads.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreateUpdateLanguageRequest {
    
    private long id;

    @NotBlank
    private String code;


    @NotBlank
    private String name;

    private boolean active;

}