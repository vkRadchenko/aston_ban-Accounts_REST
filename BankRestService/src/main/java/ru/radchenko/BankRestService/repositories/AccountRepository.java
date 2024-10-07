package ru.radchenko.BankRestService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.radchenko.BankRestService.models.Account;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Account findByAccountNumber(String accountNumber);
}
