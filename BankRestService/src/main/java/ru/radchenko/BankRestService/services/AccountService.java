package ru.radchenko.BankRestService.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.radchenko.BankRestService.exceptions.ResourceNotFoundException;
import ru.radchenko.BankRestService.models.Account;
import ru.radchenko.BankRestService.models.Transaction;
import ru.radchenko.BankRestService.models.TransactionType;
import ru.radchenko.BankRestService.models.User;
import ru.radchenko.BankRestService.repositories.AccountRepository;
import ru.radchenko.BankRestService.repositories.TransactionRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountService {

    private final   AccountRepository accountRepository;
    private final   TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;

        this.transactionRepository = transactionRepository;
    }

    @Transactional()
    public Account deposit(String accountNumber, Double amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Счет не найден с accountNumber " + accountNumber));
        account.setBalance(account.getBalance() + amount);

        // Создание транзакции
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAccount(account);

        account.addTransaction(transaction);
        transactionRepository.save(transaction);

        return accountRepository.save(account);
    }
    @Transactional()
    public Account withdraw(String accountNumber, Double amount, String password){
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Счет не найден с accountNumber " + accountNumber));

        User user = account.getUser();

        if(!user.getPassword().equals(password)) throw new RuntimeException("Неверный пароль ");
        if (account.getBalance() < amount) throw new RuntimeException("No many");

        account.setBalance(account.getBalance() - amount);

        // Создание транзакции
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setType(TransactionType.WITHDRAWAL);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAccount(account);

        account.addTransaction(transaction);
        transactionRepository.save(transaction);

        return accountRepository.save(account);
    }

    public Double getBalance(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Счет не найден с id " + accountNumber));
        return account.getBalance();
    }
    @Transactional()
    public void transfer(String fromAccountNumber,String toAccountNumber,Double amount, String password ){
        if (fromAccountNumber.equals(toAccountNumber)) {
            throw new IllegalArgumentException("Исходный и целевой счета не должны совпадать");
        }
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber).orElseThrow(() -> new ResourceNotFoundException("Исходный счет не найден с идентификатором " + fromAccountNumber));
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber).orElseThrow(() -> new ResourceNotFoundException("Исходный счет не найден с идентификатором " + toAccountNumber));

        User user = fromAccount.getUser();

        if(!user.getPassword().equals(password)) throw new RuntimeException("Неверный пароль ");
        if (fromAccount.getBalance() < amount) throw new RuntimeException("No many");

        // Снятие средств с исходного счета
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        accountRepository.save(fromAccount);

        // Запись транзакции
        Transaction withdrawalTransaction = new Transaction();
        withdrawalTransaction.setAmount(amount);
        withdrawalTransaction.setType(TransactionType.TRANSFER_OUT);
        withdrawalTransaction.setTransactionDate(LocalDateTime.now());
        withdrawalTransaction.setAccount(fromAccount);
        fromAccount.addTransaction(withdrawalTransaction);
        transactionRepository.save(withdrawalTransaction);

        accountRepository.save(fromAccount);

        // Внесение средств на другой счет
        toAccount.setBalance(toAccount.getBalance() + amount);

        // Запись транзакции
        Transaction depositTransaction = new Transaction();
        depositTransaction.setAmount(amount);
        depositTransaction.setType(TransactionType.TRANSFER_TO);
        depositTransaction.setTransactionDate(LocalDateTime.now());
        depositTransaction.setAccount(toAccount);
        toAccount.addTransaction(depositTransaction);
        transactionRepository.save(depositTransaction);

        accountRepository.save(toAccount);

    }

    @Transactional
    public Account createAccount(User user){

        String accountNumber =  generateUniqueAccountNumber();
        Account account = new Account(accountNumber,user);
        user.addAccount(account);
        accountRepository.save(account);

        return account;
    }

    private String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            accountNumber = UUID.randomUUID().toString().replace("-","").substring(0,10);
        } while (accountRepository.findByAccountNumber(accountNumber).isPresent());

        return accountNumber;
    }
}
