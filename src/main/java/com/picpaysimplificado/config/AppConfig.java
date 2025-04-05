package main.java.com.picpaysimplificado.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableRetry
public class AppConfig {
    
    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);  
        return new RestTemplate();
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        
        // Configurando política de espera - aguarda 1 segundo antes de tentar novamente
        FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(1000); // 1 segundo
        retryTemplate.setBackOffPolicy(backOffPolicy);
        
        // Configurando política de retry - tenta 3 vezes para exceções específicas
        Map<Class<? extends Throwable>, Boolean> retryableExceptions = new HashMap<>();
        retryableExceptions.put(Exception.class, true);
        
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy(
            3, // máximo de tentativas
            retryableExceptions
        );
        retryTemplate.setRetryPolicy(retryPolicy);
        
        return retryTemplate;
    }
}
