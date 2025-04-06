package main.java.com.picpaysimplificado.service;

import com.picpaysimplificado.domain.model.Transaction;
import com.picpaysimplificado.domain.model.User;
import com.picpaysimplificado.dto.TransactionDTO;
import com.picpaysimplificado.repository.TransactionRepository;
import com.picpaysimplificado.service.UserService;
import com.picpaysimplificado.service.external.AuthorizationService;
import com.picpaysimplificado.service.external.NotificationService;

import main.java.com.picpaysimplificado.application.TransferServiceInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionService implements TransferServiceInterface {
    
    private final UserService userService;
    private final TransactionRepository repository;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;

    public TransactionService(
            UserService userService, 
            TransactionRepository repository,
            AuthorizationService authorizationService,
            NotificationService notificationService) {
        this.userService = userService;
        this.repository = repository;
        this.authorizationService = authorizationService;
        this.notificationService = notificationService;
    }
    
    @Override
    @Transactional
    public Transaction transfer(Long senderId, Long receiverId, BigDecimal amount) {
        User sender = userService.findById(senderId);
        User receiver = userService.findById(receiverId);
        
        userService.validateTransaction(sender, amount);
        
        boolean isAuthorized = authorizationService.authorizeTransaction(sender, amount);
        
        if (!isAuthorized) {
            throw new IllegalStateException("Transação não autorizada pelo serviço externo");
        }
        
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setTimestamp(LocalDateTime.now());
        
        // Atualiza os saldos
        userService.updateBalance(sender, amount.negate());
        userService.updateBalance(receiver, amount);
        
        // Salva a transação
        repository.save(transaction);
        
        // Envia notificações
        notificationService.sendNotification(sender, "Transferência realizada com sucesso");
        notificationService.sendNotification(receiver, "Você recebeu uma transferência");
        
        return transaction;
    }
    
    @Transactional
    public Transaction createTransaction(TransactionDTO dto) {
        return transfer(dto.getSenderId(), dto.getReceiverId(), dto.getAmouunt());
    }
    
    public Transaction findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transação não encontrada"));
    }
}
