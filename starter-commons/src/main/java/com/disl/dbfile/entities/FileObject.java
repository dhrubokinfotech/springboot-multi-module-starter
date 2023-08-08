package com.disl.dbfile.entities;

import com.disl.commons.models.AuditModel;
import com.disl.dbfile.constants.AppTables.FileObjectTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = FileObjectTable.TABLE_NAME)
public class FileObject extends AuditModel<String> {

    @Column(name = FileObjectTable.FILE_NAME)
    private String fileName;

    @Column(name = FileObjectTable.FILE_TYPE)
    private String fileType;

    @Column(name = FileObjectTable.FILE_EXTENSION)
    private String fileExtension;

    @Column(name = FileObjectTable.MIME_TYPE)
    private String mimeType;

    @Column(name = FileObjectTable.FILE_KEY)
    private String fileKey;

    @Column(name = FileObjectTable.FILE_SIZE)
    private Double fileSize;

    @Transient
    private String fileUrl;

    @Column(name = FileObjectTable.EXTERNAL_ID)
    private Long externalId;
}
