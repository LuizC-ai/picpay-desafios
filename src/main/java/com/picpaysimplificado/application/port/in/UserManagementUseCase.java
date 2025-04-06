package main.java.com.picpaysimplificado.application.port.in;

import com.picpaysimplificado.domain.model.User;
import com.picpaysimplificado.application.dto.UserDTO;

import java.util.List;

public interface UserManagementUseCase {
    User createUser(UserDTO userDTO);
    User findById(Long id);
    List<User> findAll();
}
