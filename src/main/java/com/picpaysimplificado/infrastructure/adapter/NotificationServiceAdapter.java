package main.java.com.picpaysimplificado.infrastructure.adapter;

import com.picpaysimplificado.application.port.out.NotifyUserPort;
import com.picpaysimplificado.domain.model.User;
import com.picpaysimplificado.infrastructure.client.NotificationDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@Component
public class NotificationServiceAdapter implements NotifyUserPort {

    private static final Logger logger = Logger.getLogger(NotificationServiceAdapter.class.getName());
    private final RestTemplate restTemplate;
    private final String notificationUrl = "https://run.mocky.io/v3/54dc2cf1-3add-45b5-b5a9-6bf7e7f1f4a6";

    public NotificationServiceAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void notify(User user, String message) {
        try {
            NotificationDto notificationRequest = new NotificationDto(user.getEmail(), message);
            
            ResponseEntity<Void> response = restTemplate.postForEntity(
                    notificationUrl, 
                    notificationRequest, 
                    Void.class);
                    
            if (response.getStatusCode() != HttpStatus.OK) {
                logger.warning("Erro ao enviar notificação: " + response.getStatusCode());
            }
        } catch (Exception e) {
            logger.severe("Erro ao enviar notificação: " + e.getMessage());
        }
    }
}
