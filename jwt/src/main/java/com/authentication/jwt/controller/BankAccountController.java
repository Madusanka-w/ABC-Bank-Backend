package com.authentication.jwt.controller;

import com.authentication.jwt.config.JwtService;
import com.authentication.jwt.models.entities.BankAccount;
import com.authentication.jwt.models.entities.User;
import com.authentication.jwt.repository.BankAccountRepository;
import com.authentication.jwt.service.BankAccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class BankAccountController {

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/createBankAccount")
    public User createBankAccount(HttpServletRequest request, @RequestBody BankAccount bankAccount) {
        try {
            User user = jwtService.getAuthUser(request);

            System.out.println(user.toString());
            return bankAccountService.createBankAccount(user, bankAccount);
        } catch (Exception e){
            return new User();
        }
    }


    @GetMapping("/bankAccounts")
    public List<BankAccount> getAllBankAccounts(){
        return bankAccountService.getAllBankAccounts();
    }

    @GetMapping("/getBankAccountofUser")
    public List<BankAccount> getAllBankAccountofUser(HttpServletRequest request){
        User user = jwtService.getAuthUser(request);
        return bankAccountService.getBankAccountsOfUser(user);
    }

    @GetMapping("/bankAccount/{id}")
    public BankAccount getBankAccountById(@PathVariable Long id){
        return bankAccountService.findsById(id);
    }

    @DeleteMapping("/deleteBankAccount/{id}")
    public String deleteBankAccount(@PathVariable long id){
        try {
            return bankAccountService.deleteBankAccount(id);
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @GetMapping("/pdf")
    public void generateBankDetails(HttpServletResponse response){
        response.setContentType("application/pdf");

        try {
            bankAccountService.generatePdf(response);
        }catch (Exception e){

        }
    }


}
