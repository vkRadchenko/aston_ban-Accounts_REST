package ru.radchenko.BankRestService.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Имя получателя обязательно для заполнения")
    private String name;

    @NotBlank(message = "Пин-код обязателен для заполнения")
    @Pattern(regexp = "\\d{4}", message = "Пин-код должен состоять из четырех цифр")
    @Column(name = "password", nullable = false)
    private Integer password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Account> accounts = new ArrayList<>();

    public User() {
    }
    public User(String name, Integer password) {
        this.name = name;
        this.password = password;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getPassword() {
        return password;
    }
    public void  Integer (Integer password) {
        this.password = password;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void addAccount(Account account) {
        accounts.add(account);
        account.setUser(this);
    }

    public void removeAccount(Account account) {
        accounts.remove(account);
        account.setUser(null);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return Objects.equals(id, user.id);
    }

    @Override
    public String toString() {
        return "Recipient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pinCode='****'" + //
                '}';
    }


}
