package main.java.com.picpaysimplificado.application.port.out;

import com.picpaysimplificado.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface LoadUserPort {
    User loadById(Long id);
    Optional<User> findByDocument(String document);
    Optional<User> findByEmail(String email);
    List<User> loadAll();
}
