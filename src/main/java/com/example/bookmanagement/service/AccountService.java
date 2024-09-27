package com.example.bookmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookmanagement.model.Account;
import com.example.bookmanagement.model.Book;
import com.example.bookmanagement.repository.AccountRepository;
import com.example.bookmanagement.repository.BookRepository;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BookRepository bookRepository;

    public Account auth(Account account) {
        Account dbAccount = accountRepository.findByName(account.getName());
        if (dbAccount != null) {
            if (dbAccount.getPassword().equals(account.getPassword())) {
                return dbAccount;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account getAccountById(int id) {
        return accountRepository.findById(id).orElse(null);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public List<Book> getBooksByAccountId(Integer accountId) {
        return bookRepository.findByAccount_Id(accountId);
    }
}
