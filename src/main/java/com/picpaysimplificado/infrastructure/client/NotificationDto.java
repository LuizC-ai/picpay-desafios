package main.java.com.picpaysimplificado.infrastructure.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {

    @NotNull(message = "Email não pode ser nulo")
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotNull(message = "Mensagem não pode ser nula")
    @NotBlank(message = "Mensagem é obrigatória")
    private String message;
}
