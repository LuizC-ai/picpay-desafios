package main.java.com.picpaysimplificado.application.port.out;

import com.picpaysimplificado.domain.model.Transaction;

public interface LoadTransactionPort {
    Transaction loadById(Long id);
}
