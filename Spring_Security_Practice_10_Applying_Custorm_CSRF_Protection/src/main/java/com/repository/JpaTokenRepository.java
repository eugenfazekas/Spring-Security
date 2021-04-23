package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entities.Token;

import java.util.Optional;

import javax.transaction.Transactional;

public interface JpaTokenRepository extends JpaRepository<Token, Integer> {

    Optional<Token> findTokenByIdentifier(String identifier);
    
    @Transactional
    @Modifying
    @Query("update Token u set u.token = :token where u.identifier = :identifier")
    void updateTokenByIdentifier(@Param(value = "identifier") String identifier, @Param(value = "token") String token);
}
