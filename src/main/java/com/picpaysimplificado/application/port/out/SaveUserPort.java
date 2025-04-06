package main.java.com.picpaysimplificado.application.port.out;

import com.picpaysimplificado.domain.model.User;

public interface SaveUserPort {
    User save(User user);
}
