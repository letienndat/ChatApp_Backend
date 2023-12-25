package com.chatappspringboot.service;

import com.chatappspringboot.dto.request.FormAccount;
import com.chatappspringboot.entity.Account;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    Account findById(String username) throws Exception;
    Account save(Account account);
    Account updateAccount(FormAccount formAccount) throws Exception;
    boolean existsAccountByUsername(String username);
    boolean existsAccount(String username, String password);
}
