package com.disl.localization.language.service;

import com.disl.commons.constants.CommonUtils;
import com.disl.commons.exceptions.ResponseException;
import com.disl.commons.payloads.PaginationArgs;
import com.disl.localization.language.entities.Language;
import com.disl.localization.language.payloads.request.CreateUpdateLanguageRequest;
import com.disl.localization.language.repository.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.disl.commons.specification.AppSpecification.getSpecification;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    public Language save(Language language) {
        return languageRepository.save(language);
    }

    public Language createUpdate(CreateUpdateLanguageRequest request, Language language) {
        language.setCode(request.getCode());
        language.setName(request.getName());
        language.setActive(request.isActive());
        return languageRepository.save(language);
    }

    public List<Language> getAllLanguages() {
        return languageRepository.findAll();
    }

    public Language findById(long id) {
        return languageRepository.findById(id).orElse(null);
    }

   public Boolean existsByCode(String code){
        return languageRepository.existsByCode(code);
   }

    public boolean existsByCodeAndActive(String code, boolean active) {
        return languageRepository.existsByCodeAndActive(code, active);
    }

    public Language findByIdAndActive(long id) {
        return languageRepository.findTopByIdAndActive(id, true);
    }

    public Language findTopByCode(String code) {
        return languageRepository.findTopByCode(code).orElse(null);
    }

    public Language findTopByCodeWithException(String code) {
        return languageRepository.findTopByCode(code).orElseThrow(()->
                new ResponseException(HttpStatus.NOT_FOUND, "Language not found with code: " + code + "!"));
    }

    public Language findByCodeAndName(String code, String name) {
        return languageRepository.findTopByCodeAndName(code, name);
    }

    public Language findByCodeAndNameAndIdNot(String code, String name, long id) {
        return languageRepository.findTopByCodeAndNameAndIdNot(code, name, id);
    }

    public Language findTopByCodeAndIdNot(String code,long id){

        return languageRepository.findTopByCodeAndIdNot(code,id);

    }

    public List<Language> findTotal(Map<String, Object> parameters) {
        Map<String, Object> specParameters = CommonUtils.getParameters(parameters);
        if(!specParameters.isEmpty()) {
            return languageRepository.findAll(getSpecification(specParameters));
        }
        
        return languageRepository.findAll();
    }

    public Page<Language> findAllPaginated(PaginationArgs paginationArgs) {
        Pageable pageable = CommonUtils.getPageable(paginationArgs);
        Map<String, Object> specParameters = CommonUtils.getParameters(paginationArgs.getParameters());

        if(!specParameters.isEmpty()) {
            return languageRepository.findAll(getSpecification(specParameters), pageable);
        }
        
        return languageRepository.findAll(pageable);
    }

    public void delete(Language language) {
        //TODO - delete all other things which is dependent into language
        languageRepository.delete(language);
    }
}
