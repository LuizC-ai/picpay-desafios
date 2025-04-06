package main.java.com.picpaysimplificado.domain.model;

import com.picpaysimplificado.domain.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private BigDecimal amount;
    
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
    
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private boolean active = true;
    
    private LocalDateTime timestamp;
    
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    
    // Factory method para criar uma transação válida
    public static Transaction create(User sender, User receiver, BigDecimal amount) {
        if (sender.equals(receiver)) {
            throw new IllegalArgumentException("Remetente e destinatário não podem ser iguais");
        }
        
        if (!sender.canTransfer(amount)) {
            throw new IllegalArgumentException("Usuário não pode realizar esta transferência");
        }
        
        Transaction transaction = new Transaction();
        transaction.setSender(sender);
        transaction.setReceiver(receiver);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setStatus(TransactionStatus.PENDING);
        transaction.setActive(true);
        
        return transaction;
    }
    
    public void markAsCompleted() {
        this.status = TransactionStatus.COMPLETED;
    }
    
    public void markAsFailed() {
        this.status = TransactionStatus.FAILED;
    }
    
    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
