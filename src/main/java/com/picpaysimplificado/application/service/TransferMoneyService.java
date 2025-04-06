package main.java.com.picpaysimplificado.application.service;

import com.picpaysimplificado.application.port.in.TransferUseCase;
import com.picpaysimplificado.application.port.out.*;
import com.picpaysimplificado.domain.exception.TransferNotAllowedException;
import com.picpaysimplificado.domain.model.Transaction;
import com.picpaysimplificado.domain.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransferMoneyService implements TransferUseCase {

    private final LoadUserPort loadUserPort;
    private final SaveTransactionPort saveTransactionPort;
    private final SaveUserPort saveUserPort;
    private final AuthorizeTransactionPort authorizeTransactionPort;
    private final NotifyUserPort notifyUserPort;

    public TransferMoneyService(
            LoadUserPort loadUserPort,
            SaveTransactionPort saveTransactionPort,
            SaveUserPort saveUserPort,
            AuthorizeTransactionPort authorizeTransactionPort,
            NotifyUserPort notifyUserPort) {
        this.loadUserPort = loadUserPort;
        this.saveTransactionPort = saveTransactionPort;
        this.saveUserPort = saveUserPort;
        this.authorizeTransactionPort = authorizeTransactionPort;
        this.notifyUserPort = notifyUserPort;
    }

    @Override
    @Transactional
    public Transaction transfer(Long senderId, Long receiverId, BigDecimal amount) {
        // 1. Buscar usuários
        User sender = loadUserPort.loadById(senderId);
        User receiver = loadUserPort.loadById(receiverId);
        
        // 2. Validar a transferência
        validateTransfer(sender, amount);
        
        // 3. Autorizar a transação
        boolean isAuthorized = authorizeTransactionPort.authorize(sender, amount);
        
        if (!isAuthorized) {
            throw new TransferNotAllowedException("Transação não autorizada pelo serviço externo");
        }
        
        // 4. Criar transação
        Transaction transaction = Transaction.create(sender, receiver, amount);
        
        // 5. Efetuar a transferência no domínio
        sender.debit(amount);
        receiver.credit(amount);
        
        // 6. Persistir mudanças
        saveUserPort.save(sender);
        saveUserPort.save(receiver);
        transaction.markAsCompleted();
        Transaction savedTransaction = saveTransactionPort.save(transaction);
        
        // 7. Notificar usuários
        notifyUserPort.notify(sender, "Transferência realizada com sucesso");
        notifyUserPort.notify(receiver, "Você recebeu uma transferência");
        
        return savedTransaction;
    }
    
    private void validateTransfer(User sender, BigDecimal amount) {
        if (sender.isMerchant()) {
            throw new TransferNotAllowedException("Lojistas não podem realizar transferências");
        }
        
        if (!sender.hasEnoughBalance(amount)) {
            throw new TransferNotAllowedException("Saldo insuficiente");
        }
    }
}
