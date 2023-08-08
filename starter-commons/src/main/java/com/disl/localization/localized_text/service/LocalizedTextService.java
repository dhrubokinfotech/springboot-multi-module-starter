package com.disl.localization.localized_text.service;

import com.disl.commons.constants.CommonUtils;
import com.disl.commons.exceptions.ResponseException;
import com.disl.commons.payloads.LanguageWiseTextRequest;
import com.disl.localization.language.entities.Language;
import com.disl.localization.language.service.LanguageService;
import com.disl.localization.localized_text.entities.LocalizedText;
import com.disl.localization.localized_text.repository.LocalizedTextRepository;
import com.disl.localization.translation.entities.Translation;
import com.disl.localization.translation.service.TranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalizedTextService {

    @Autowired
    private TranslationService translationService;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private LocalizedTextRepository localizedTextRepository;

    public LocalizedText save(LocalizedText localizedText) {
        return localizedTextRepository.save(localizedText);
    }

    public LocalizedText findById(long id) {
        return localizedTextRepository.findById(id).orElse(null);
    }
    
    public LocalizedText createUpdate(Translation translation, LocalizedText localizedText) {
        if(localizedText == null) {
            localizedText = new LocalizedText();
        }

        localizedText.setOriginalText(translation.getTranslatedText());
        localizedText.setOriginalLanguageCode(translation.getLanguageCode());
        return localizedTextRepository.save(localizedText);
    }

    public LocalizedText createLocalizedText(String text, String languageCode) {
        LocalizedText localizedText = new LocalizedText();
        localizedText.setOriginalText(text);
        localizedText.setOriginalLanguageCode(languageCode);

        return localizedTextRepository.save(localizedText);
    }

    public LocalizedText createUpdateLocalizedText(
            List<LanguageWiseTextRequest> languageWiseTextRequests, Long localizedTextId,
            boolean isThrowNotFoundException, String fieldName
    ) {
        LocalizedText localizedText = null;
        List<Translation> translations = translationService.getLanguageWiseTranslations(languageWiseTextRequests, isThrowNotFoundException, fieldName);

        if(!CommonUtils.isNullOrEmptyList(translations)) {
            if(CommonUtils.isNotNullAndGreaterZero(localizedTextId)) {
                localizedText = getLocalizedText(localizedTextId);
                localizedText = createUpdate(translations.get(0), localizedText);
            } else {
                localizedText = createUpdate(translations.get(0), new LocalizedText());
            }

            translations = translationService.getLocalizedTranslations(translations, localizedText);
            translationService.saveAll(translations);
        }

        return localizedText;
    }

    private LocalizedText getLocalizedText(Long localizedTextId) {
        LocalizedText localizedText = findById(localizedTextId);
        if(localizedText != null) {
            translationService.deleteAllByLocalizedText(localizedText);
        }

        return localizedText;
    }
    
    @Async
    public void deleteAllByIdAsync(Long id) {
        if(CommonUtils.isNotNullAndGreaterZero(id)) {
            localizedTextRepository.deleteAllById(id);
        }
    }

    public LocalizedText createLocalizedTextWithTranslation (String text, String languageCode) {
        Language language = this.languageService.findTopByCodeWithException(languageCode);

        LocalizedText localizedText = this.createLocalizedText(text, languageCode);
        this.translationService.createTranslation(text, language, localizedText);
        return localizedText;
    }

    public LocalizedText updateLocalizedTextWithTranslation(LocalizedText localizedText, String text, String languageCode) {
        Language language = languageService.findTopByCodeWithException(languageCode);

        if (!localizedText.getOriginalLanguageCode().equals(language.getCode())) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Language not matched!");
        }

        localizedText.setOriginalText(text);
        localizedText.setOriginalLanguageCode(languageCode);
        LocalizedText updatedLocalizedText = save(localizedText);
        this.translationService.updateTranslation(text, language, updatedLocalizedText);

        return updatedLocalizedText;
    }

}
