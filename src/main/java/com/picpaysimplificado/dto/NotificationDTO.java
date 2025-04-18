package main.java.com.picpaysimplificado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {

    @NotNull(message = "Email não pode ser nulo")
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Formato de email inválido")
    private String email;

    @NotNull(message = "Mensagem não pode ser nula")
    @NotBlank(message = "Mensagem é obrigatória")
    private String message;
}
