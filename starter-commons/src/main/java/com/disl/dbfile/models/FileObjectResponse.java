package com.disl.dbfile.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileObjectResponse {

    private Long id;

    private String fileName;

    private String fileType;

    private String fileExtension;

    private String mimeType;

    private String fileKey;

    private Double fileSize;

    private String fileUrl;
}
