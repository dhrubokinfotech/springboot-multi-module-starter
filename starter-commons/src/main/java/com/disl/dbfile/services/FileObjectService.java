package com.disl.dbfile.services;

import com.disl.commons.exceptions.ResponseException;
import com.disl.commons.payloads.Response;
import com.disl.dbfile.entities.FileObject;
import com.disl.dbfile.models.FileObjectResponse;
import com.disl.dbfile.repositories.FileObjectRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.disl.dbfile.constants.AppConstants.*;

@Service
public class FileObjectService {

    @Autowired
    private FileObjectRepository fileObjectRepository;

    @Autowired
    private RestTemplate restTemplate;

    public List<FileObject> getAllFileObjects() {
        List<FileObject> fileObjects = fileObjectRepository.findAll();
        return fileObjects.stream().map(this::setExternalFileMetaData).toList();
    }

    public FileObject setExternalFileMetaData (FileObject fileObject) {
        if (fileObject != null) {
            FileObjectResponse fileObjectResponse = findByExternalId(fileObject.getExternalId());
            if (fileObjectResponse == null) {
                return null;
            }
            fileObject.setFileUrl(fileObjectResponse.getFileUrl());
        }
        return fileObject;
    }

    public FileObject findById(Long fileObjectId) {
        FileObject fileObject = fileObjectRepository.findById(fileObjectId).orElse(null);
        if (fileObject != null) {
            FileObjectResponse fileObjectResponse = findByExternalId(fileObject.getExternalId());
            if (fileObjectResponse == null) {
                return null;
            }
            fileObject.setFileUrl(fileObjectResponse.getFileUrl());
        }
        return fileObject;
    }

    public FileObjectResponse findByExternalId(Long externalId) {
        String fileInfoUrl = FILE_INFO_URL + externalId;
        ResponseEntity<Response> response;
        try {
             response = restTemplate.exchange(
                    fileInfoUrl,
                    HttpMethod.GET,
                    null,
                    Response.class
            );
        } catch (Exception ex) {
            return null;
        }

        if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
            if (!response.getBody().isSuccess()) {
                return null;
            }

            return new ObjectMapper().convertValue(response.getBody().getPayload(), FileObjectResponse.class);
        }
        else {
            return null;
        }
    }

    public ResponseEntity<ByteArrayResource> downloadFile(Long fileObjectId) throws IOException {
        FileObject fileObject = fileObjectRepository.findById(fileObjectId).orElseThrow(()->
                new ResponseException(HttpStatus.NOT_FOUND, "File object not found with id: " + fileObjectId + "."));

        FileObjectResponse fileObjectResponse = findByExternalId(fileObject.getExternalId());
        if (fileObjectResponse == null) {
            throw new ResponseException(HttpStatus.NOT_FOUND, "Error occurred while finding file.");
        }

        String fileName = fileObjectResponse.getFileName();
        String fileDownloadUrl = FILE_DOWNLOAD_URL + "?name=" + fileName;

        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        ResponseEntity<byte[]> response;
        try {
            response = restTemplate.exchange(
                    fileDownloadUrl,
                    HttpMethod.GET,
                    null,
                    byte[].class
            );
        } catch (Exception ex) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Error occurred while downloading file.");
        }

        if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null && response.getBody().length > 0) {
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CACHE_CONTROL, CacheControl.noCache().getHeaderValue());
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName);

            byte[] fileBytes = response.getBody();

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(response.getBody().length)
                    .contentType(MediaType.parseMediaType(fileObjectResponse.getMimeType()))
                    .body(new ByteArrayResource(fileBytes));
        }
        else {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Error occurred while downloading file.");
        }
    }

    public FileObject saveFileObject(FileObject fileObject) {
        return fileObjectRepository.save(fileObject);
    }

    public FileObject uploadDbFile(MultipartFile file, String folderPath) throws IOException {
        FileObjectResponse fileObjectResponse =  storeFile(file, folderPath);

        FileObject fileObject = new FileObject();
        fileObject.setFileName(fileObjectResponse.getFileName());
        fileObject.setFileType(fileObjectResponse.getFileType());
        fileObject.setFileExtension(fileObjectResponse.getFileExtension());
        fileObject.setMimeType(fileObjectResponse.getMimeType());
        fileObject.setFileKey(fileObjectResponse.getFileKey());
        fileObject.setFileSize(fileObjectResponse.getFileSize());
        fileObject.setExternalId(fileObjectResponse.getId());

        FileObject savedFileObject = saveFileObject(fileObject);
        savedFileObject.setFileUrl(fileObjectResponse.getFileUrl());
        return savedFileObject;
    }

    public FileObjectResponse storeFile(MultipartFile file, String folderPath) throws IOException {
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("folderPath", folderPath);
        params.add("file", new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename();
            }
        });

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, headers);

        ResponseEntity<Response> response;
        try {
            response = restTemplate.exchange(
                    FILE_UPLOAD_URL,
                    HttpMethod.POST,
                    requestEntity,
                    Response.class
            );
        } catch (Exception ex) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Error occurred while processing file.");
        }

        if (response.getStatusCode().equals(HttpStatus.OK) && response.getBody() != null) {
            if (!response.getBody().isSuccess()) {
                throw new ResponseException(HttpStatus.BAD_REQUEST, "Error occurred while processing file.");
            }

            return new ObjectMapper().convertValue(response.getBody().getPayload(), FileObjectResponse.class);
        }
        else {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Error occurred while processing file.");
        }
    }

    public void deleteFile(Long externalId) {
        String deleteUrl = FILE_DELETE_URL + externalId;
        ResponseEntity<Response> response;
        try {
            response = restTemplate.exchange(
                    deleteUrl,
                    HttpMethod.DELETE,
                    null,
                    Response.class
            );
        } catch (Exception ex) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Error occurred while deleting file in file handler.");
        }
    }

    public void deleteFileObject(Long fileObjectId){
        FileObject fileObject = fileObjectRepository.findById(fileObjectId).orElseThrow(()->
                new ResponseException(HttpStatus.NOT_FOUND, "File object not found with id: " + fileObjectId + "."));

        deleteFile(fileObject.getExternalId());
        fileObjectRepository.delete(fileObject);
    }

    public void deletePrevFileObject(FileObject prevFileObject) {
        if (prevFileObject != null) {
            deleteFile(prevFileObject.getExternalId());
            fileObjectRepository.delete(prevFileObject);
        }
    }

    public void deletePrevFileObject(FileObject prevFileObject, FileObject currentFileObject) {
        if (prevFileObject != null && currentFileObject != null && !prevFileObject.equals(currentFileObject)) {
            deleteFile(prevFileObject.getExternalId());
            fileObjectRepository.delete(prevFileObject);
        }
    }
}
