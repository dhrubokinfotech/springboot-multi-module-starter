package com.disl.startercore.features.language.controllers;

import com.disl.commons.payloads.Response;
import com.disl.localization.language.entities.Language;
import com.disl.localization.language.payloads.request.CreateUpdateLanguageRequest;
import com.disl.localization.language.service.LanguageService;
import com.disl.startercore.config.CommonApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CommonApiResponses
@RequestMapping(value = "/api/language")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @Operation(summary = "Get all languages")
    @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Language.class))), responseCode = "200")
    @GetMapping(value = "/all")
    public ResponseEntity<Response> getAllLanguages() {
        return Response.getResponseEntity(
                true, "Languages are loaded.", languageService.getAllLanguages());
    }

    @Operation(summary = "Create new language.", security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponse(content = @Content(schema = @Schema(implementation = Language.class)), responseCode = "200")
    @PreAuthorize("hasAuthority('LANGUAGE_CREATE')")
    @PostMapping(value = "/create")
    public ResponseEntity<Response> createNewLanguage(@RequestBody CreateUpdateLanguageRequest language) {
        return Response.getResponseEntity(
                true, "Language created successfully.", languageService.createUpdate(language, new Language()));
    }
}
