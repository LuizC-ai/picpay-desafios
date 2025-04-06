package com.picpaysimplificado.service;

import com.picpaysimplificado.domain.model.User;
import com.picpaysimplificado.dto.UserDTO;
import com.picpaysimplificado.exception.UserNotFoundException;
import com.picpaysimplificado.repository.UserRepository;

import main.java.com.picpaysimplificado.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    
    private final UserRepository repository;
    private final UserMapper mapper; 
    
    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }
    
    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }
    
    public User saveUser(UserDTO dto) {
        User user = mapper.toEntity(dto);
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
