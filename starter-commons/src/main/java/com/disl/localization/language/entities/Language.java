package com.disl.localization.language.entities;

import com.disl.commons.models.AuditModel;
import com.disl.localization.language.constants.AppTables.LanguageTable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor

@Entity
@Table(name = LanguageTable.TABLE_NAME)
public class Language extends AuditModel<String> {

    @Column(name = LanguageTable.CODE)
    private String code;

    @Column(name = LanguageTable.NAME)
    private String name;

    @Column(name = LanguageTable.ACTIVE)
    private Boolean active = true;
}
