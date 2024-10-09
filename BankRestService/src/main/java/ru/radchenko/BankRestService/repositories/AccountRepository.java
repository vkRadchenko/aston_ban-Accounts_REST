package ru.radchenko.BankRestService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.radchenko.BankRestService.models.Account;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
}
