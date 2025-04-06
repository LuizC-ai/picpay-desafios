package main.java.com.picpaysimplificado.application.port.out;

import com.picpaysimplificado.domain.model.User;

import java.math.BigDecimal;

public interface AuthorizeTransactionPort {
    boolean authorize(User sender, BigDecimal value);
}
