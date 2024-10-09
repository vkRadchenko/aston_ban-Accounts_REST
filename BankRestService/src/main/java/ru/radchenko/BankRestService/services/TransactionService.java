package ru.radchenko.BankRestService.services;

import org.springframework.stereotype.Service;
import ru.radchenko.BankRestService.exceptions.ResourceNotFoundException;
import ru.radchenko.BankRestService.models.Transaction;
import ru.radchenko.BankRestService.repositories.TransactionRepository;

import java.util.List;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Транзакция не найдена с id " + id));
    }

    public List<Transaction> getTransactionsByAccountId(Long accountNumber) {
        return transactionRepository.findByAccountId(accountNumber);
    }


}
