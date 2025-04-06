package main.java.com.picpaysimplificado.infrastructure.adapter;

import com.picpaysimplificado.application.port.out.AuthorizeTransactionPort;
import com.picpaysimplificado.domain.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class AuthorizationServiceAdapter implements AuthorizeTransactionPort {

    private static final Logger logger = Logger.getLogger(AuthorizationServiceAdapter.class.getName());
    
    private final RestTemplate restTemplate;
    private final String authorizationUrl;

    public AuthorizationServiceAdapter(
            RestTemplate restTemplate, 
            @Value("${authorization.service.url:https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc}") String authorizationUrl) {
        this.restTemplate = restTemplate;
        this.authorizationUrl = authorizationUrl;
    }

    @Override
    @Retryable(value = RestClientException.class, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public boolean authorize(User sender, BigDecimal value) {
        logger.info("Autorizando transação para usuário: " + sender.getId() + " com valor: " + value);
        
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(authorizationUrl, Map.class);
                
            if (response.getStatusCode() == HttpStatus.OK) {
                String message = (String) response.getBody().get("message");
                boolean authorized = "Autorizado".equalsIgnoreCase(message);
                
                logger.info("Transação " + (authorized ? "autorizada" : "não autorizada"));
                return authorized;
            }
            
            logger.warning("Serviço de autorização retornou status: " + response.getStatusCode());
            return false;
        } catch (RestClientException e) {
            logger.severe("Erro ao comunicar com serviço de autorização: " + e.getMessage());
            throw e; // Relança para o @Retryable funcionar
        } catch (Exception e) {
            logger.severe("Erro inesperado: " + e.getMessage());
            return false;
        }
    }
}
