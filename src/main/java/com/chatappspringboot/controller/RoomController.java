package com.chatappspringboot.controller;

import com.chatappspringboot.dto.response.FormGetRoom;
import com.chatappspringboot.dto.response.FormGetUser;
import com.chatappspringboot.dto.response.MessageResponse;
import com.chatappspringboot.dto.response.TypeMessage;
import com.chatappspringboot.service.impl.RoomServiceImpl;
import com.chatappspringboot.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/room")
public class RoomController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoomServiceImpl roomService;

    @GetMapping("/from-search/{id}")
    public ResponseEntity<MessageResponse> getRoomFromSearch(@PathVariable(name = "id") Long id) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        FormGetUser user = userService.getByUsername(username);
        FormGetRoom formGetRoom = roomService.get(user.getId(), id);

        return ResponseEntity.ok(new MessageResponse(TypeMessage.SUCCESS, formGetRoom));
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<MessageResponse> showRoom(@Valid @PathVariable Long id) throws Exception {
        FormGetRoom formGetRoom = roomService.get(id);

        return ResponseEntity.ok(new MessageResponse(TypeMessage.SUCCESS, formGetRoom));
    }
}
