package com.chatappspringboot.controller;

import com.chatappspringboot.dto.request.FormLineChat;
import com.chatappspringboot.dto.response.FormGetLineChat;
import com.chatappspringboot.dto.response.MessageResponse;
import com.chatappspringboot.dto.response.TypeMessage;
import com.chatappspringboot.service.impl.LineChatServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/line-chat")
public class LineChatController {
    @Autowired
    private LineChatServiceImpl lineChatService;

//    @PostMapping
//    public ResponseEntity<MessageResponse> save(@Valid @RequestBody FormLineChat formLineChat) throws Exception {
//        FormGetLineChat formGetLineChat = lineChatService.save(formLineChat);
//
//        return ResponseEntity.ok(new MessageResponse(TypeMessage.SUCCESS, formGetLineChat));
//    }

    @GetMapping("/{idRoom}")
    public ResponseEntity<MessageResponse> get(@Valid @PathVariable(name = "idRoom") Long idRoom) throws Exception {
        List<FormGetLineChat> formGetLineChats = lineChatService.get(idRoom);

        return ResponseEntity.ok(new MessageResponse(TypeMessage.SUCCESS, formGetLineChats));
    }
}
