package main.java.com.picpaysimplificado.infrastructure.adapter;

import com.picpaysimplificado.application.port.out.LoadUserPort;
import com.picpaysimplificado.application.port.out.SaveUserPort;
import com.picpaysimplificado.domain.exception.UserNotFoundException;
import com.picpaysimplificado.domain.model.User;
import com.picpaysimplificado.infrastructure.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserJpaAdapter implements LoadUserPort, SaveUserPort {

    private final UserJpaRepository userRepository;

    public UserJpaAdapter(UserJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    @Override
    public Optional<User> findByDocument(String document) {
        return userRepository.findByDocument(document);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<User> loadAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
