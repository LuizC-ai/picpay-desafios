package main.java.com.picpaysimplificado.infrastructure.repository;

import com.picpaysimplificado.domain.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionJpaRepository extends JpaRepository<Transaction, Long> {
}
