package com.chatappspringboot.controller;

import com.chatappspringboot.dto.response.FormGetUser;
import com.chatappspringboot.dto.response.FormLoadUser;
import com.chatappspringboot.dto.response.MessageResponse;
import com.chatappspringboot.dto.response.TypeMessage;
import com.chatappspringboot.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/load")
    public ResponseEntity<MessageResponse> loadHome() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        FormGetUser user = userService.getByUsername(username);
        FormLoadUser formLoadUser = userService.load(user.getId());

        return ResponseEntity.ok(
                new MessageResponse(
                        TypeMessage.SUCCESS,
                        formLoadUser
                )
        );
    }

    @GetMapping("/show-setting")
    public ResponseEntity<MessageResponse> showSetting() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        FormGetUser user = userService.getByUsername(username);

        return ResponseEntity.ok(
                new MessageResponse(
                        TypeMessage.SUCCESS,
                        user
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<MessageResponse> search(@RequestParam(name = "keyword") String keyword) {
        List<FormGetUser> formGetUsers = userService.getBySearchTerm(keyword);
        return ResponseEntity.ok(
                new MessageResponse(
                        TypeMessage.SUCCESS,
                        formGetUsers
                )
        );
    }
}
