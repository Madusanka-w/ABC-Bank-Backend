package com.authentication.jwt.models.entities;

import com.authentication.jwt.models.enums.AccountType;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long accountBalance;

    private String accountNumber;

    private AccountType type = AccountType.SAVING;

    @OneToMany
    private List<Transaction> transactions;


}
