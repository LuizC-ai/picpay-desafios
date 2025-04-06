package main.java.com.picpaysimplificado.infrastructure.web;

import com.picpaysimplificado.application.dto.TransactionDTO;
import com.picpaysimplificado.application.port.in.TransferUseCase;
import com.picpaysimplificado.application.port.out.LoadTransactionPort;
import com.picpaysimplificado.domain.model.Transaction;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    
    private final TransferUseCase transferUseCase;
    private final LoadTransactionPort loadTransactionPort;
    
    public TransactionController(
            TransferUseCase transferUseCase,
            LoadTransactionPort loadTransactionPort) {
        this.transferUseCase = transferUseCase;
        this.loadTransactionPort = loadTransactionPort;
    }
    
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody @Valid TransactionDTO transactionDTO) {
        Transaction newTransaction = transferUseCase.transfer(
                transactionDTO.getSenderId(),
                transactionDTO.getReceiverId(),
                transactionDTO.getAmount());
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Transaction transaction = loadTransactionPort.loadById(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}
