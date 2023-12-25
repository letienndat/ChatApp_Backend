package com.chatappspringboot.service.impl;

import com.chatappspringboot.dto.request.FormAccount;
import com.chatappspringboot.entity.Account;
import com.chatappspringboot.repository.AccountRepository;
import com.chatappspringboot.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Account findById(String username) throws Exception {
        Optional<Account> account = accountRepository.findById(username);

        return account.orElseThrow(() -> new Exception("Can not find account by username = " + username));
    }

    @Override
    public Account save(Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));

        return accountRepository.save(account);
    }

    @Override
    public Account updateAccount(FormAccount formAccount) throws Exception {
        Account account = accountRepository.findById(formAccount.getUsername()).orElseThrow(() -> new Exception("Can not find account by username = " + formAccount.getUsername()));
        account.setPassword(passwordEncoder.encode(formAccount.getPassword()));

        return accountRepository.save(account);
    }

    @Override
    public boolean existsAccountByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    @Override
    public boolean existsAccount(String username, String password) {
        return accountRepository.existsAccount(username, password);
    }
}
