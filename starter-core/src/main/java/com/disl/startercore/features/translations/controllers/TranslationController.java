package com.disl.startercore.features.translations.controllers;

import com.disl.commons.payloads.Response;
import com.disl.startercore.config.CommonApiResponses;
import com.disl.localization.translation.entities.Translation;
import com.disl.localization.translation.models.requests.TranslationRequest;
import com.disl.localization.translation.service.TranslationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CommonApiResponses
@RequestMapping(value = "/api/translation")
public class TranslationController {

    @Autowired
    private TranslationService translationService;

    @Operation(summary = "Get all translation by localizedText id", security =  @SecurityRequirement(name = "jwtToken"))
    @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Translation.class))), responseCode = "200")
    @GetMapping("/localized-text/{localizedTextId}/all")
    public ResponseEntity<Response> findAllByLocalizedText(@PathVariable Long localizedTextId) {
        return Response.getResponseEntity(
                true, "Data loaded successfully.", translationService.findAllByLocalizedText(localizedTextId));
    }

    @Operation(summary = "Get translation info by id", security =  @SecurityRequirement(name = "jwtToken"))
    @ApiResponse(content = @Content(schema = @Schema(implementation = Translation.class)), responseCode = "200")
    @GetMapping(value = "/id/{translationId}")
    public ResponseEntity<Response> findById(@PathVariable Long translationId) {
        return Response.getResponseEntity(
                true, "Data loaded successfully.", translationService.findByIdWithException(translationId));
    }

    @Operation(summary = "New translation creation", security =  @SecurityRequirement(name = "jwtToken"))
    @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Translation.class))), responseCode = "200")
    @PostMapping("/create")
    public ResponseEntity<Response> createNewTranslation(@Valid @RequestBody TranslationRequest request) {
        return Response.getResponseEntity(
                true, "Translation created successfully.", translationService.createTranslationFromRequest(request));
    }

    @Operation(summary = "Update translation info", security =  @SecurityRequirement(name = "jwtToken"))
    @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Translation.class))), responseCode = "200")
    @PutMapping("/update")
    public ResponseEntity<Response> updateTranslation(@Valid @RequestBody TranslationRequest request) {
        return Response.getResponseEntity(
                true, "Translation updated successfully.", translationService.updateTranslationFromRequest(request));
    }

    @Operation(summary = "Delete translation", security =  @SecurityRequirement(name = "jwtToken"))
    @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = Response.class))), responseCode = "200")
    @DeleteMapping("/id/{translationId}/delete")
    public ResponseEntity<Response> deleteTranslation(@PathVariable Long translationId) {
        translationService.deleteTranslation(translationId);
        return Response.getResponseEntity(true, "Translation deleted successfully.");
    }
}
