package main.java.com.picpaysimplificado.service.external;

import com.picpaysimplificado.domain.model.User;
import com.picpaysimplificado.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationService {
    
    @Autowired
    private RestTemplate restTemplate;
    
    public void sendNotification(User user, String message) {
        // Simula notificação por email ou push
        NotificationDTO notificationRequest = new NotificationDTO(
                user.getEmail(),
                message
        );
        
        // Envia a notificação para um serviço externo
        // Em produção, seria uma chamada real para um serviço de notificação
        ResponseEntity<Void> response = restTemplate.postForEntity(
                "https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6", 
                notificationRequest, 
                Void.class);
                
        if (response.getStatusCode() != HttpStatus.OK) {
            System.out.println("Erro ao enviar notificação");
            // Em uma implementação real, poderíamos registrar isso ou tentar novamente
        }
    }
}
