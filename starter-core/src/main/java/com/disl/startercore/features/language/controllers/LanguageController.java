package com.disl.startercore.features.language.controllers;

import com.disl.commons.payloads.Response;
import com.disl.localization.language.entities.Language;
import com.disl.localization.language.payloads.request.CreateUpdateLanguageRequest;
import com.disl.localization.language.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/language")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @GetMapping(value = "/all")
    public ResponseEntity<Response> getAllLanguages() {
        return Response.getResponseEntity(
                true, "Languages are loaded.", languageService.getAllLanguages());
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Response> createNewLanguage(@RequestBody CreateUpdateLanguageRequest language) {
        return Response.getResponseEntity(
                true, "Language created successfully.", languageService.createUpdate(language, new Language()));
    }
}
