package main.java.com.picpaysimplificado.domain.exception;

public class InsufficientBalanceException extends DomainException {
    public InsufficientBalanceException() {
        super("Saldo insuficiente para realizar esta transação");
    }
}
