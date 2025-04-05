package main.java.com.picpaysimplificado.service;

import com.picpaysimplificado.domain.model.Transaction;
import com.picpaysimplificado.domain.model.User;
import com.picpaysimplificado.dto.TransactionDTO;
import com.picpaysimplificado.repository.TransactionRepository;
import com.picpaysimplificado.service.external.AuthorizationService;
import com.picpaysimplificado.service.external.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TransactionService {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private TransactionRepository repository;
    
    @Autowired
    private AuthorizationService authorizationService;
    
    @Autowired
    private NotificationService notificationService;
    
    @Transactional
    public Transaction createTransaction(TransactionDTO dto) {
        User sender = userService.findById(dto.getSenderId());
        User receiver = userService.findById(dto.getReceiverId());
        
        userService.validateTransaction(sender, dto.getValue());
        
        boolean isAuthorized = authorizationService.authorizeTransaction(sender, dto.getValue());
        
        if (!isAuthorized) {
            throw new IllegalStateException("Transação não autorizada pelo serviço externo");
        }
        
        Transaction transaction = new Transaction();
        transaction.setAmount(dto.getValue());
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTimestamp(LocalDateTime.now());
        
        // Atualiza os saldos
        userService.updateBalance(sender, dto.getValue().negate());
        userService.updateBalance(receiver, dto.getValue());
        
        // Salva a transação
        repository.save(transaction);
        
        // Envia notificações
        notificationService.sendNotification(sender, "Transferência realizada com sucesso");
        notificationService.sendNotification(receiver, "Você recebeu uma transferência");
        
        return transaction;
    }
    
    public Transaction findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada"));
    }
}
