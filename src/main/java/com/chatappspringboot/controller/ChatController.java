package com.chatappspringboot.controller;

import com.chatappspringboot.dto.request.FormLineChat;
import com.chatappspringboot.dto.response.FormGetLineChat;
import com.chatappspringboot.entity.Account;
import com.chatappspringboot.service.impl.AccountServiceImpl;
import com.chatappspringboot.service.impl.LineChatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import java.util.Base64;

@Controller
public class ChatController {
    @Autowired
    private LineChatServiceImpl lineChatService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MessageMapping("/chat/{idRoom}")
    public void sendMessage(@DestinationVariable Long idRoom, @Payload FormLineChat formLineChat) throws Exception {
        String auth = formLineChat.getAuth();
        if (auth != null && auth.startsWith("Basic ")) {
            String base64Credentials = auth.substring("Basic ".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials));

            final String[] values = credentials.split(":", 2);
            if (values.length == 2) {
                Account account = accountService.findById(values[0]);
                boolean isPasswordMatch = passwordEncoder.matches(values[1], account.getPassword());
                if (isPasswordMatch) {
                    FormGetLineChat formGetLineChat = lineChatService.save(formLineChat, values[0]);
                    messagingTemplate.convertAndSend("/topic/messages/" + idRoom, formGetLineChat);
                }
            }
        }
    }
}
