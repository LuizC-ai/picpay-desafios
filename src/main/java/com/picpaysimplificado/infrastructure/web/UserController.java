package main.java.com.picpaysimplificado.infrastructure.web;

import com.picpaysimplificado.application.dto.UserDTO;
import com.picpaysimplificado.application.port.in.UserManagementUseCase;
import com.picpaysimplificado.domain.model.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserManagementUseCase userManagementUseCase;
    
    public UserController(UserManagementUseCase userManagementUseCase) {
        this.userManagementUseCase = userManagementUseCase;
    }
    
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid UserDTO userDTO) {
        User newUser = userManagementUseCase.createUser(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
    
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userManagementUseCase.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userManagementUseCase.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
