package com.picpaysimplificado.repository;

import com.picpaysimplificado.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByDocument(String document);
    Optional<User> findByEmail(String email);
}
