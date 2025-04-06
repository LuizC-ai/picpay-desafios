package main.java.com.picpaysimplificado.application.service;

import com.picpaysimplificado.application.dto.UserDTO;
import com.picpaysimplificado.application.port.in.UserManagementUseCase;
import com.picpaysimplificado.application.port.out.LoadUserPort;
import com.picpaysimplificado.application.port.out.SaveUserPort;
import com.picpaysimplificado.domain.model.User;
import com.picpaysimplificado.domain.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserManagementService implements UserManagementUseCase {

    private final SaveUserPort saveUserPort;
    private final LoadUserPort loadUserPort;
    private final UserMapper userMapper;

    public UserManagementService(
            SaveUserPort saveUserPort,
            LoadUserPort loadUserPort,
            UserMapper userMapper) {
        this.saveUserPort = saveUserPort;
        this.loadUserPort = loadUserPort;
        this.userMapper = userMapper;
    }

    @Override
    public User createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        return saveUserPort.save(user);
    }

    @Override
    public User findById(Long id) {
        return loadUserPort.loadById(id);
    }

    @Override
    public List<User> findAll() {
        return loadUserPort.loadAll();
    }
}
