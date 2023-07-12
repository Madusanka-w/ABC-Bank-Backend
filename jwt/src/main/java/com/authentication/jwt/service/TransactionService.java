package com.authentication.jwt.service;

import com.authentication.jwt.models.entities.BankAccount;
import com.authentication.jwt.models.entities.Transaction;
import com.authentication.jwt.models.enums.TransactionType;
import com.authentication.jwt.repository.BankAccountRepository;
import com.authentication.jwt.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private BankAccountService bankAccountService;


    public BankAccount deposit(Long bankAccountID, Long amount){
        BankAccount bankAccount = bankAccountService.findsById(bankAccountID);
        Transaction transaction = new Transaction();
        bankAccount.setAccountBalance(bankAccount.getAccountBalance() + amount);
        long balance = bankAccount.getAccountBalance();
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAmount(amount);

        List<Transaction> transactionList = bankAccount.getTransactions();
        transactionList.add(transaction);
        bankAccount.setTransactions(transactionList);
        transactionRepository.save(transaction);
        return bankAccountRepository.save(bankAccount);
    }


    public BankAccount withdrawAmount(long accID, long amount){
        BankAccount account = bankAccountService.findsById(accID);
        Transaction transaction = new Transaction();

        if(amount>account.getAccountBalance()){
            return new BankAccount();
        }
        else {
            account.setAccountBalance(account.getAccountBalance() - amount);
            long balance = account.getAccountBalance();
            transaction.setType(TransactionType.WITHDRAWAL);
            transaction.setAmount(amount);

            List<Transaction> transactionList = account.getTransactions();
            transactionList.add(transaction);
            account.setTransactions(transactionList);
            transactionRepository.save(transaction);
            return bankAccountRepository.save(account);
        }

    }

//    public String transferAmount(Transaction transaction){
//        long amount = transaction.getAmount();
//        long sourceAccountID = transaction.getAccountNumber();
//        BankAccount sourceAccount = bankAccountService.findsById(sourceAccountID);
//        long destAccountID = transaction.getDestAccountID();
//        Accounts destAccount = accountService.findById(destAccountID).orElseThrow(() -> new RuntimeException("Account not found"));
//        long sourceBalance  = sourceAccount.getAccountBalance();
//        long destBalance = destAccount.getAccountBalance();
//
//        if(amount>sourceBalance){
//            return "Not enough balance";
//        }
//        else{
//            long newSourceBalance = sourceBalance - amount;
//            long newDestBalance = destBalance + amount;
//            sourceAccount.setAccountBalance(newSourceBalance);
//            destAccount.setAccountBalance(newDestBalance);
//
//            //create transaction records for source and destination accounts
//            Transaction sourceTransaction = new Transaction();
//            Transaction destTransaction = new Transaction();
//            sourceTransaction.setBalance(newSourceBalance);
//            sourceTransaction.setType(TransType.TRANSFER);
//            sourceTransaction.setTransferAmount(amount);
//            sourceTransaction.setSourceAccountID(sourceAccountID);
//            sourceTransaction.setDestAccountID(destAccountID);
//
//            destTransaction.setType(TransType.DEPOSIT);
//            destTransaction.setBalance(newDestBalance);
//            destTransaction.setTransferAmount(amount);
//            destTransaction.setSourceAccountID(sourceAccountID);
//            destTransaction.setDestAccountID(destAccountID);
//
//            List<Transaction> transactionSourceList = sourceAccount.getTransaction();
//            transactionSourceList.add(sourceTransaction);
//            sourceAccount.setTransaction(transactionSourceList);
//            transactionRepository.save(sourceTransaction);
//            accountRepository.save(sourceAccount);
//
//            List<Transaction> transactionDestList = destAccount.getTransaction();
//            transactionDestList.add(destTransaction);
//            destAccount.setTransaction(transactionDestList);
//            transactionRepository.save(destTransaction);
//            accountRepository.save(destAccount);
//
//            return "transfer successful";
//        }
//
//    }
//
//    public List<Transaction> viewTransactionsOfASpecificUser(long accID){
//        Optional<Accounts> account = accountService.findById(accID);
//        return account.get().getTransaction();
//    }
}
