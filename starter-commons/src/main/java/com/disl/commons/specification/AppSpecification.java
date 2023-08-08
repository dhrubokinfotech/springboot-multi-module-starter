package com.disl.commons.specification;

import com.disl.localization.localized_text.entities.LocalizedText;
import com.disl.localization.translation.entities.Translation;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class AppSpecification {

    private AppSpecification() {}

    public static <T> Specification<T> getSpecification(Map<String, Object> parameters) {
         return Specification.where((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                String filterBy = entry.getKey();
                String filterWith = entry.getValue().toString();

                if(filterWith != null && !filterWith.isEmpty()) {
                    Class<?> type = root.get(filterBy).getJavaType();

                    if (type.equals(Long.class)) {
                        predicates.add(criteriaBuilder.equal(root.get(filterBy), Long.valueOf(filterWith)));
                    } else if (type.equals(Boolean.class)) {
                        predicates.add(criteriaBuilder.equal(root.get(filterBy), Boolean.valueOf(filterWith)));
                    } else if (type.equals(String.class)) {
                        predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(filterBy)), "%" + filterWith.toUpperCase() + "%"));
                    } else if (type.equals(LocalDateTime.class)) {
                        LocalDate localDate = LocalDate.parse(filterWith);
                        LocalDateTime startDateTime = LocalDateTime.of(localDate, LocalTime.MIN);
                        LocalDateTime endDateTime = LocalDateTime.of(localDate, LocalTime.MAX);
                        predicates.add(criteriaBuilder.between(root.get(filterBy), startDateTime, endDateTime));
                    } else if (type.isEnum()) {
                        Enum<?> enumValue = Enum.valueOf((Class<Enum>) type, filterWith);
                        predicates.add(criteriaBuilder.equal(root.get(filterBy), enumValue));
                    } else {
                        predicates.add(criteriaBuilder.equal(root.get(filterBy), filterWith));
                    }
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        });
    }

    public static <T>Specification<T> getLocalizedSpecification(Map<String, Object> parameters) {
        return Specification.where((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                String filterBy = entry.getKey();
                String filterWith = entry.getValue().toString();

                if (filterWith != null && !filterWith.isEmpty()) {
                    Class<?> type = root.get(filterBy).getJavaType();

                    if (type.equals(Long.class)) {
                        predicates.add(criteriaBuilder.equal(root.get(filterBy), Long.valueOf(filterWith)));
                    } else if (type.equals(Boolean.class)) {
                        predicates.add(criteriaBuilder.equal(root.get(filterBy), Boolean.valueOf(filterWith)));
                    } else if (type.equals(String.class)) {
                        predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get(filterBy)), "%" + filterWith.toUpperCase() + "%"));
                    } else if (type.equals(LocalDateTime.class)) {
                        LocalDate localDate = LocalDate.parse(filterWith);
                        LocalDateTime startDateTime = LocalDateTime.of(localDate, LocalTime.MIN);
                        LocalDateTime endDateTime = LocalDateTime.of(localDate, LocalTime.MAX);
                        predicates.add(criteriaBuilder.between(root.get(filterBy), startDateTime, endDateTime));
                    }
                    else if (type.isEnum()) {
                        Enum<?> enumValue = Enum.valueOf((Class<Enum>) type, filterWith);
                        predicates.add(criteriaBuilder.equal(root.get(filterBy), enumValue));
                    }
                    else if (type.equals(LocalizedText.class)){
                        Join<T, LocalizedText> localizedTextJoin = root.join(filterBy);
                        Join<LocalizedText, Translation> translationJoin = localizedTextJoin.join("translations");
                        predicates.add(criteriaBuilder.like(criteriaBuilder.upper(translationJoin.get("translatedText")), "%" +  filterWith.toUpperCase() + "%"));
                    } else {
                        predicates.add(criteriaBuilder.equal(root.get(filterBy), filterWith));
                    }
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        });
    }
}
