package main.java.com.picpaysimplificado.application.service;

import com.picpaysimplificado.application.dto.UserDTO;
import com.picpaysimplificado.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    
    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setDocument(dto.getDocument());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setBalance(dto.getBalance());
        user.setUserType(dto.getUserType());
        return user;
    }
    
    public UserDTO toDto(User entity) {
        UserDTO dto = new UserDTO();
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setDocument(entity.getDocument());
        dto.setEmail(entity.getEmail());
        dto.setBalance(entity.getBalance());
        dto.setUserType(entity.getUserType());
        return dto;
    }
}
