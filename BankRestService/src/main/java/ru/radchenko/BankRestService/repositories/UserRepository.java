package ru.radchenko.BankRestService.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.radchenko.BankRestService.models.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
