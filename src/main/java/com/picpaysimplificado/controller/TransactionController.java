package main.java.com.picpaysimplificado.controller;

import com.picpaysimplificado.domain.model.Transaction;
import com.picpaysimplificado.dto.TransactionDTO;
import com.picpaysimplificado.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    
    private TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }
    
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody @Valid TransactionDTO transactionDTO) {
        Transaction newTransaction = service.createTransaction(transactionDTO);
        return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        Transaction transaction = service.findById(id);
        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}
