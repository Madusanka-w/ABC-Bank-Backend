package com.authentication.jwt.service;

import com.authentication.jwt.models.entities.BankAccount;
import com.authentication.jwt.models.entities.Transaction;
import com.authentication.jwt.models.entities.User;
import com.authentication.jwt.repository.BankAccountRepository;
import com.authentication.jwt.repository.UserRepository;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private UserRepository userRepository;

    public User createBankAccount(User user, BankAccount bankAccount) {
        List<BankAccount> bankAccounts = user.getBankAccount();
        bankAccounts.add(bankAccount);
        user.setBankAccount(bankAccounts);
        return userRepository.save(user);
    }

    public BankAccount findsById(Long bankAccountID) {
        return bankAccountRepository.findById(bankAccountID).orElseThrow(() -> new EntityNotFoundException("Bank account not found"));
    }

    public String deleteBankAccount(Long bankAccountID){
        BankAccount bankAccount = bankAccountRepository.findById(bankAccountID).orElseThrow(() -> new EntityNotFoundException("Bank account not found"));
        bankAccountRepository.delete(bankAccount);

        return "Bank Account with ID "+ bankAccountID +" deleted.";

    }

    public List<BankAccount> getBankAccountsOfUser(User user) {
        List<BankAccount> bankAccounts = user.getBankAccount();
        return bankAccounts;
    }

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }


    public void generatePdf(Long id, HttpServletResponse response) throws IOException {

        //create a pdf document with a a4 size
        Document document = new Document(PageSize.A4);

        BankAccount bankAccount = bankAccountRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("not found"));
        System.out.println("Bank Account" +bankAccount);
        List<Transaction> transaction =  bankAccount.getTransactions();
        System.out.println("transaction" +transaction);

        //create a pdf write to actually edit the document
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());

        //open the document for editing
        document.open();

        //create paragraph text to add to the document
        Paragraph paragraph = new Paragraph("Transaction List");

        //add paragraph
        document.add(paragraph);

        //create table with 2 column
        PdfPTable table = new PdfPTable(2);

        //set width of the table with respect to the page size
        table.setWidthPercentage(100f);

        //create a cell in the table
        PdfPCell headerCell = new PdfPCell();

        headerCell.setPhrase(new Phrase("Amount"));

        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Type"));

        table.addCell(headerCell);

        for (Transaction transaction1: transaction){
            table.addCell(String.valueOf(transaction1.getAmount()));
            table.addCell(String.valueOf(transaction1.getType()));
        }



        document.add(table);


        document.close();
        writer.close();
    }


}
