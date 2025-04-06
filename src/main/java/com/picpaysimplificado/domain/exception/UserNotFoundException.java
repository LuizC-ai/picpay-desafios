package main.java.com.picpaysimplificado.domain.exception;

public class UserNotFoundException extends DomainException {
    public UserNotFoundException(String message) {
        super(message);
    }
    
    public UserNotFoundException(Long id) {
        super("Usuário com id " + id + " não encontrado");
    }
}
