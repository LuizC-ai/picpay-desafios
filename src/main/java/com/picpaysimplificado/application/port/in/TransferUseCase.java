package main.java.com.picpaysimplificado.application.port.in;

import com.picpaysimplificado.domain.model.Transaction;

import java.math.BigDecimal;

public interface TransferUseCase {
    Transaction transfer(Long senderId, Long receiverId, BigDecimal amount);
}
