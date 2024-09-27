package com.example.bookmanagement.controller;

import com.example.bookmanagement.model.Account;
import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping
    public Account createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable int id) {
        return accountService.getAccountById(id);
    }

    @GetMapping("/{id}/books")
    public List<Book> getBooksByAccountId(@PathVariable Integer id) {
        return accountService.getBooksByAccountId(id);
    }
}