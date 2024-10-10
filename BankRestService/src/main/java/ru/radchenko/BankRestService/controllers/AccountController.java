package ru.radchenko.BankRestService.controllers;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.radchenko.BankRestService.models.Account;
import ru.radchenko.BankRestService.models.User;
import ru.radchenko.BankRestService.services.AccountService;
import ru.radchenko.BankRestService.services.UserService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final UserService userService;

    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @PostMapping("/{accountNumber}/deposit")
    public ResponseEntity<Account> deposit(@PathVariable String accountNumber, @RequestParam Double amount) {
        Account updatedAccount = accountService.deposit(accountNumber, amount);
        return ResponseEntity.ok(updatedAccount);
    }

    @PostMapping("/user/{userId}")
    public ResponseEntity<Account> addedAccount(@PathVariable Long userId) {
            User user = userService.getUserById(userId);
            Account account = accountService.createAccount(user);
            return ResponseEntity.ok(account);
         //ResponseEntity.notFound().build();
    }

    @PostMapping("/{accountNumber}/withdraw")
    public ResponseEntity<Account> withdraw(@PathVariable String accountNumber,
                                            @RequestParam Double amount,
                                            @RequestParam String password) {
        System.out.println("password " + password);
        Account updatedAccount = accountService.withdraw(accountNumber, amount, password);
        return ResponseEntity.ok(updatedAccount);
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam String fromAccountNumber,
                                            @RequestParam String toAccountNumber,
                                            @RequestParam Double amount,
                                            @RequestParam String password){

        accountService.transfer(fromAccountNumber,toAccountNumber,amount,password);
        return ResponseEntity.ok("Good transfer");
    }

    @GetMapping("/{accountNumber}/balance")
    public ResponseEntity<Double> getBalance(@PathVariable String accountNumber) {
        Double balance = accountService.getBalance(accountNumber);
        return ResponseEntity.ok(balance);
    }


}
