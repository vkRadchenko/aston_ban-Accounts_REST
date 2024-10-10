package ru.radchenko.BankRestService.events;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.radchenko.BankRestService.models.Account;
import ru.radchenko.BankRestService.models.User;
import ru.radchenko.BankRestService.repositories.UserRepository;
import ru.radchenko.BankRestService.services.AccountService;

@Component
public class AccountEventListener {
     private final AccountService accountService;
     private final UserRepository userRepository;

    public AccountEventListener(AccountService accountService, UserRepository userRepository) {
        this.accountService = accountService;
        this.userRepository = userRepository;
    }

    @EventListener
    public String autoCreateAccountToFirstAuthorization(UserCreatedEvent event) {
        User user = userRepository.findById(event.userId())
                .orElseThrow(() -> new RuntimeException("Получатель не найден с ID: " + event.userId()));

        Account account = accountService.createAccount(user);
        return account.getAccountNumber();
    }
}
