package com.example.bookmanagement.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bookmanagement.jwt.JwtUtils;
import com.example.bookmanagement.jwt.PassToken;
import com.example.bookmanagement.jwt.TokenInfo;
import com.example.bookmanagement.model.Account;
import com.example.bookmanagement.service.AccountService;

@RestController
public class AuthController {
    @Autowired(required = false)
    private AccountService accountService;

    @PassToken
    @RequestMapping("/welcome/auth")
    public String auth() {
        Account account = new Account();
        account.setId(1);
        account.setName("lgc653");
        account.setPassword("lgc653");
        account.setEmail("lgc653@qq.com");
        return JwtUtils.createToken(account);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/auth")
    public TokenInfo auth(String loginId, String loginPassword) {
        Account account = new Account();
        account.setName(loginId);
        account.setPassword(loginPassword);
        Account dbAccount = accountService.auth(account);
        if (dbAccount != null) {
            return new TokenInfo(JwtUtils.createToken(account), 200, "");
        } else {
            return new TokenInfo("", 500, "登录失败，用户名密码错误");
        }
    }

}