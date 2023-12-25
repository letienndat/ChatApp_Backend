package com.chatappspringboot.controller;

import com.chatappspringboot.dto.request.FormAccount;
import com.chatappspringboot.dto.request.FormSignUp;
import com.chatappspringboot.dto.response.FormGetUser;
import com.chatappspringboot.dto.response.MessageResponse;
import com.chatappspringboot.dto.response.TypeMessage;
import com.chatappspringboot.entity.Account;
import com.chatappspringboot.service.impl.AccountServiceImpl;
import com.chatappspringboot.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @PostMapping("/signin")
    public ResponseEntity<MessageResponse> signin(@Valid @RequestBody FormAccount formAccount) throws Exception {
        Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(formAccount.getUsername(), formAccount.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        if (authentication.isAuthenticated()) {
            FormGetUser formGetUser = userService.getByUsername(formAccount.getUsername());
            return ResponseEntity.ok(
                    new MessageResponse(TypeMessage.SUCCESS, formGetUser)
            );
        }

        return ResponseEntity.ok(
                new MessageResponse(TypeMessage.FALD, "Thông tin đăng nhập không chính xác, hãy thử lại!")
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> signup(@Valid @RequestBody FormSignUp formSignUp) throws Exception {
        if (accountService.existsAccountByUsername(formSignUp.getUsername())) {
            return ResponseEntity.ok(
                    new MessageResponse(TypeMessage.FALD, "Tên người dùng này đã tồn tại!")
            );
        }
        accountService.save(new Account(formSignUp.getUsername(), formSignUp.getPassword()));
        FormGetUser formGetUser = userService.save(formSignUp.getUsername(), formSignUp.getName());

        return ResponseEntity.ok(
                new MessageResponse(TypeMessage.SUCCESS, formGetUser)
        );
    }
}
