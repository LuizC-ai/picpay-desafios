package main.java.com.picpaysimplificado.domain.exception;

public class TransferNotAllowedException extends DomainException {
    public TransferNotAllowedException(String message) {
        super(message);
    }
}
