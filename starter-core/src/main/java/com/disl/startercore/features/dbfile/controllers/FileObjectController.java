package com.disl.startercore.features.dbfile.controllers;

import com.disl.commons.payloads.Response;
import com.disl.startercore.config.CommonApiResponses;
import com.disl.dbfile.entities.FileObject;
import com.disl.dbfile.services.FileObjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.disl.dbfile.constants.AppConstants.*;

@RestController
@CommonApiResponses
@RequestMapping("/api/file-object")
public class FileObjectController {

    @Autowired
    private FileObjectService fileObjectService;

    @Operation(summary = "Get all file objects", security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponse(content = @Content(array = @ArraySchema(schema = @Schema(implementation = FileObject.class))), responseCode = "200")
    @GetMapping(value = "/all")
    public ResponseEntity<Response> getAllFileObjects() {
        return Response.getResponseEntity(
                true, "Data loaded successfully.", fileObjectService.getAllFileObjects());
    }

    @Operation(summary = "Get file object info by id", security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponse(content = @Content(schema = @Schema(implementation = FileObject.class)), responseCode = "200")
    @GetMapping(value = "/id/{fileObjectId}")
    public ResponseEntity<com.disl.commons.payloads.Response> findById(@PathVariable Long fileObjectId) {
        FileObject fileObject = fileObjectService.findById(fileObjectId);
        if (fileObject == null) {
            return Response.getResponseEntity(HttpStatus.NOT_FOUND, "File object not found with id: " + fileObjectId + ".");
        }

        return Response.getResponseEntity(
                true, "Data loaded successfully.", fileObject);
    }

    @Operation(summary = "Upload file object", security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponse(content = @Content(schema = @Schema(implementation = FileObject.class)), responseCode = "200")
    @PostMapping(value = "/upload")
    public ResponseEntity<Response> uploadFileObject(@RequestParam(name = FILE) MultipartFile file, @RequestParam(name = FOLDER_PATH) String folderPath) throws IOException {
        return Response.getResponseEntity(
                true, "File uploaded successfully.", fileObjectService.uploadDbFile(file, folderPath));
    }

    @Operation(summary = "Download file", security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponse(content = @Content(schema = @Schema(implementation = FileObject.class)), responseCode = "200")
    @GetMapping(value = "/id/{fileObjectId}/download")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Long fileObjectId) throws IOException {
        return fileObjectService.downloadFile(fileObjectId);
    }

    @Operation(summary = "Delete file object by id", security = @SecurityRequirement(name = "jwtToken"))
    @ApiResponse(content = @Content(schema = @Schema(implementation = Response.class)), responseCode = "200")
    @DeleteMapping(value = "/id/{fileObjectId}/delete")
    public ResponseEntity<Response> deleteFileObject(@PathVariable Long fileObjectId) {
        fileObjectService.deleteFileObject(fileObjectId);
        return Response.getResponseEntity(
                true, "File deleted successfully.");
    }
}
