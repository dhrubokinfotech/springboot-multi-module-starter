package com.disl.localization.translation.models.requests;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TranslationRequest {

    private Long id;

    @NotBlank(message = "Translated text is required.")
    private String translatedText;

    private Long localizedTextId;

    private String languageCode;
}
