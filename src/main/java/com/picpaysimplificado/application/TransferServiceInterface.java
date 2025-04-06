package main.java.com.picpaysimplificado.application;

import java.math.BigDecimal;

public interface TransferServiceInterface {

    Transaction transfer(Long senderId, Long receiverId, BigDecimal amount);
    
}
