package com.disl.localization.localized_text.entities;

import com.disl.commons.constants.CommonConstants;
import com.disl.commons.models.AuditModel;
import com.disl.localization.localized_text.constants.AppTables.LocalizedTextTable;
import com.disl.localization.translation.entities.Translation;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor

@Entity
@Table(name = LocalizedTextTable.TABLE_NAME)
public class LocalizedText extends AuditModel<String> {

    @Column(name = LocalizedTextTable.ORIGINAL_TEXT, columnDefinition = CommonConstants.DEFAULT_LARGE_TEXT_DATA_TYPE)
    private String originalText;

    @Column(name = LocalizedTextTable.ORIGINAL_LANGUAGE_CODE)
    private String originalLanguageCode;

    @OneToMany(mappedBy = "localizedText", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    List<Translation> translations = new ArrayList<>();
}
