package com.disl.localization.language.repository;

import com.disl.localization.language.entities.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long>, JpaSpecificationExecutor<Language> {

    Optional<Language> findTopByCode(String code);

    boolean existsByCode(String code);

    Language findTopByName(String name);

    Language findTopByIdAndActive(long id, boolean active);

    Language findTopByCodeAndName(String code, String name);

    boolean existsByCodeAndActive(String code, boolean active);

    Language findTopByCodeAndNameAndIdNot(String code, String name, long id);

    Language findTopByCodeAndIdNot(String code,long id);


}
