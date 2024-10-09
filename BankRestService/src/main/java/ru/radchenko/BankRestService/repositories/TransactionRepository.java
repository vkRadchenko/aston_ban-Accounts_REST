package ru.radchenko.BankRestService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.radchenko.BankRestService.models.Transaction;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findByAccountId(Long accountId);
}
