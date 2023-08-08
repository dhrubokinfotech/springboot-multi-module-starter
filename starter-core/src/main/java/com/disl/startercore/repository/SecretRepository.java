package com.disl.startercore.repository;

import com.disl.startercore.entities.Secret;
import com.disl.startercore.enums.UserTokenPurpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SecretRepository extends JpaRepository<Secret, Long> {
    Optional<Secret> findSecretByUserId(Long userid);

    Optional<Secret> findByUserIdAndUserTokenPurpose(Long userid, UserTokenPurpose userTokenPurpose);

    Optional<Secret> findByUserTokenAndUserTokenPurpose(String userToken , UserTokenPurpose userTokenPurpose);

    @Transactional
    void deleteAllByUserId(long userId);
}
