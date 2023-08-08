package com.disl.localization.translation.entities;

import com.disl.commons.constants.CommonConstants;
import com.disl.commons.models.AuditModel;
import com.disl.localization.localized_text.entities.LocalizedText;
import com.disl.localization.translation.constants.AppTables.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString

@Entity
@Table(name = TranslationTable.TABLE_NAME)
public class Translation extends AuditModel<String> {
   
    @Column(name = TranslationTable.TRANSLATED_TEXT, columnDefinition = CommonConstants.DEFAULT_LARGE_TEXT_DATA_TYPE)
    private String translatedText;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = TranslationTable.LOCALIZED_TEXT_ID)
    private LocalizedText localizedText;

    @Column(name = TranslationTable.LANGUAGE_ID)
    private Long languageId;

    @Column(name = TranslationTable.LANGUAGE_CODE)
    private String languageCode;
}
