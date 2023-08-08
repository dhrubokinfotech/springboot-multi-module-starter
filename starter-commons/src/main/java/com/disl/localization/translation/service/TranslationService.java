package com.disl.localization.translation.service;

import com.disl.commons.constants.CommonUtils;
import com.disl.commons.exceptions.ResponseException;
import com.disl.commons.payloads.LanguageWiseTextRequest;
import com.disl.localization.language.entities.Language;
import com.disl.localization.language.service.LanguageService;
import com.disl.localization.localized_text.entities.LocalizedText;
import com.disl.localization.localized_text.repository.LocalizedTextRepository;
import com.disl.localization.translation.entities.Translation;
import com.disl.localization.translation.models.requests.TranslationRequest;
import com.disl.localization.translation.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TranslationService {

    @Autowired
    private LanguageService languageService;

    @Autowired
    private TranslationRepository translationRepository;

    @Autowired
    private LocalizedTextRepository localizedTextRepository;

    public Translation saveTranslation(Translation translation) {
        return translationRepository.save(translation);
    }

    public List<Translation> saveAll(List<Translation> translations) {
        return translationRepository.saveAll(translations);
    }

    public List<Translation> findAllByLocalizedText(Long localizedTextId) {
        LocalizedText localizedText = localizedTextRepository.findById(localizedTextId).orElseThrow(()->
                new ResponseException(HttpStatus.NOT_FOUND, "Localized text not found!"));
        return translationRepository.findAllByLocalizedText(localizedText);
    }

    public List<Translation> getLocalizedTranslations(List<Translation> translations, LocalizedText localizedText) {
        return translations.stream().peek(item -> item.setLocalizedText(localizedText)).collect(Collectors.toList());
    }

    public Translation findById(Long translationId) {
        return translationRepository.findById(translationId).orElse(null);
    }

    public Translation findByIdWithException(Long translationId) {
        return translationRepository.findById(translationId).orElseThrow(()->
                new ResponseException(HttpStatus.NOT_FOUND, "Translation not found with id: " + translationId + "."));
    }

    public Translation findByLocalizedTextAndLanguageCode(LocalizedText localizedText, String languageCode) {
        return translationRepository.findTopByLocalizedTextAndLanguageCode(localizedText, languageCode).orElse(null);
    }

    public Translation findByLocalizedTextAndLanguageCodeWithException(LocalizedText localizedText, String languageCode) {
        return translationRepository.findTopByLocalizedTextAndLanguageCode(localizedText, languageCode).orElseThrow(()->
                new ResponseException(HttpStatus.NOT_FOUND, "Translation not found!"));
    }

    public List<Translation> getLanguageWiseTranslations(
        List<LanguageWiseTextRequest> requests,
        boolean throwNotFoundException, String fieldName
    ) {
        if(throwNotFoundException && CommonUtils.isNullOrEmptyList(requests)) {
            throw new ResponseException(fieldName + " field can't be null or empty");
        }

        return requests.stream().map(item -> {
            long languageId = item.getLanguageId();
            Language language = languageService.findById(languageId);

            if(throwNotFoundException && language == null) {
                throw new ResponseException("No language found with this id " + languageId);
            }

            if(language != null) {
                Translation translation = new Translation();
                translation.setTranslatedText(item.getText());
                translation.setLanguageCode(language.getCode());
                translation.setLanguageId(language.getId());
                return translation;
            }

            return null;
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
    }

    public Boolean existsByLocalizedTextAndLanguageCode(LocalizedText localizedText, String languageCode) {
        return translationRepository.existsTranslationByLocalizedTextAndLanguageCode(localizedText, languageCode);
    }

    public Boolean existsByLocalizedTextAndLanguageCodeAndIdNot(LocalizedText localizedText, String languageCode, Long translationId) {
        return translationRepository.existsTranslationByLocalizedTextAndLanguageCodeAndIdNot(localizedText, languageCode, translationId);
    }

    public Translation createTranslationFromRequest(TranslationRequest request) {
        System.out.println(request.getLanguageCode());
        Language language = languageService.findTopByCodeWithException(request.getLanguageCode());

        LocalizedText localizedText = localizedTextRepository.findById(request.getLocalizedTextId()).orElseThrow(()->
                new ResponseException(HttpStatus.NOT_FOUND, "Localized text not found!"));

        if (existsByLocalizedTextAndLanguageCode(localizedText, language.getCode())) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Translation with language: " + language.getName() + " already exists!");
        }

        Translation translation = new Translation();
        translation.setTranslatedText(request.getTranslatedText());
        translation.setLocalizedText(localizedText);
        translation.setLanguageId(language.getId());
        translation.setLanguageCode(language.getCode());
        return saveTranslation(translation);
    }

    public Translation createTranslation(String text, Language language, LocalizedText localizedText) {
        Translation translation = new Translation();
        translation.setLocalizedText(localizedText);
        translation.setLanguageId(language.getId());
        translation.setLanguageCode(language.getCode());
        translation.setTranslatedText(text);
        return saveTranslation(translation);
    }

    public Translation updateTranslationFromRequest(TranslationRequest request) {
        Translation translation = findByIdWithException(request.getId());
        Language language = languageService.findTopByCodeWithException(request.getLanguageCode());

        LocalizedText localizedText = localizedTextRepository.findById(request.getLocalizedTextId()).orElseThrow(()->
                new ResponseException(HttpStatus.NOT_FOUND, "Localized text not found with id: " + request.getLocalizedTextId() + "."));

        if (!translation.getLanguageCode().equals(request.getLanguageCode())) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Language not matched!");
        }

        translation.setTranslatedText(request.getTranslatedText());
        translation.setLanguageId(language.getId());
        translation.setLanguageCode(language.getCode());
        return saveTranslation(translation);
    }

    public Translation updateTranslation(String text, Language language, LocalizedText localizedText) {
        Translation translation = findByLocalizedTextAndLanguageCode(localizedText, language.getCode());
        translation.setLocalizedText(localizedText);
        translation.setLanguageId(language.getId());
        translation.setLanguageCode(language.getCode());
        translation.setTranslatedText(text);
        return saveTranslation(translation);
    }

    public void deleteTranslation(Long translationId) {
        Translation translation = findByIdWithException(translationId);
        LocalizedText localizedText = translation.getLocalizedText();

        if (localizedText.getOriginalLanguageCode().equals(translation.getLanguageCode())) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Sorry! You can not delete base translation.");
        }

        translationRepository.delete(translation);
    }

    public void deleteAllByLocalizedText(LocalizedText localizedText) {
        if (localizedText != null) {
            translationRepository.deleteAllByLocalizedText(localizedText);
        }
    }
}
