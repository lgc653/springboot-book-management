package com.example.bookmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.bookmanagement.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Account findByName(String name);
}