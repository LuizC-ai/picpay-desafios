package com.picpaysimplificado.service;

import com.picpaysimplificado.domain.model.User;
import com.picpaysimplificado.dto.UserDTO;
import com.picpaysimplificado.exception.UserNotFoundException;
import com.picpaysimplificado.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    
    @Autowired
    private UserRepository repository;
    
    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }
    
    public User saveUser(UserDTO dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDocument(dto.getDocument());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setBalance(dto.getBalance());
        user.setUserType(dto.getUserType());
        
        return repository.save(user);
    }
    
    public List<User> getAllUsers() {
        return repository.findAll();
    }
    
    public void validateTransaction(User sender, BigDecimal amount) {
        if (sender.getUserType().toString().equals("MERCHANT")) {
            throw new IllegalArgumentException("Lojistas não podem realizar transferências");
        }
        
        if (sender.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente");
        }
    }
    
    public void updateBalance(User user, BigDecimal amount) {
        user.setBalance(user.getBalance().add(amount));
        repository.save(user);
    }
}
