package com.example.bookmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bookmanagement.model.Account;
import com.example.bookmanagement.repository.AccountRepository;

@Service
public class AccountService {
    @Autowired(required = false)
    private AccountRepository accountRepository;

    public Account auth(Account account) {
        Account dbAccount = accountRepository.findByName(account.getName());
        if (dbAccount != null) {
            // 当然一般是加密后的密码比较，不是明码比较
            if (dbAccount.getPassword().equals(account.getPassword())) {
                return dbAccount;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
