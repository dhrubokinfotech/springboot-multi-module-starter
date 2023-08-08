package com.disl.localization.translation.repository;

import com.disl.localization.localized_text.entities.LocalizedText;
import com.disl.localization.translation.entities.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TranslationRepository extends JpaRepository<Translation, Long> {

    @Transactional
    void deleteAllByLocalizedText(LocalizedText localizedText);

    List<Translation> findAllByLocalizedText(LocalizedText localizedText);

    Optional<Translation> findTopByLocalizedTextAndLanguageCode(LocalizedText localizedText, String languageCode);

    Boolean existsTranslationByLocalizedTextAndLanguageCode(LocalizedText localizedText, String languageCode);

    Boolean existsTranslationByLocalizedTextAndLanguageCodeAndIdNot(LocalizedText localizedText, String languageCode, Long translationId);
}
