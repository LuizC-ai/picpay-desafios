package main.java.com.picpaysimplificado.infrastructure.adapter;

import com.picpaysimplificado.application.port.out.LoadTransactionPort;
import com.picpaysimplificado.application.port.out.SaveTransactionPort;
import com.picpaysimplificado.domain.exception.DomainException;
import com.picpaysimplificado.domain.model.Transaction;
import com.picpaysimplificado.infrastructure.repository.TransactionJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class TransactionJpaAdapter implements SaveTransactionPort, LoadTransactionPort {

    private final TransactionJpaRepository transactionRepository;

    public TransactionJpaAdapter(TransactionJpaRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction loadById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new DomainException("Transação não encontrada") {});
    }
}
