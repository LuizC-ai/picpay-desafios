package main.java.com.picpaysimplificado.application.port.out;

import com.picpaysimplificado.domain.model.User;

public interface NotifyUserPort {
    void notify(User user, String message);
}
