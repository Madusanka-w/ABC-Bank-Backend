package com.authentication.jwt.controller;

import com.authentication.jwt.models.entities.BankAccount;
import com.authentication.jwt.service.TransactionService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @PostMapping("/transaction/{amount}/{bankAccountID}")
    public BankAccount transactionAmount(@PathVariable long bankAccountID, @PathVariable long amount){
        return transactionService.deposit(bankAccountID, amount);
    }

    @PostMapping("/withdrawal/{amount}/{id}")
    public String withdrawal(@PathVariable long id, @PathVariable long amount){
        return transactionService.withdrawAmount(id, amount);
    }

    @GetMapping("/generatePDF")
    public void generatePDF(HttpServletResponse response){
        response.setContentType("application/pdf");

        try{
            transactionService.generateUserListPdf(response);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @DeleteMapping("/deleteTransaction/{id}")
    public String deleteTransaction(@PathVariable Long id){
        try {
            return transactionService.deleteTransaction(id);
        }catch (Exception e){
            return e.getMessage();
        }
    }

}
