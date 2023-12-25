package com.chatappspringboot.service.impl;

import com.chatappspringboot.dto.request.FormLineChat;
import com.chatappspringboot.dto.response.FormGetLineChat;
import com.chatappspringboot.entity.LineChat;
import com.chatappspringboot.entity.Room;
import com.chatappspringboot.entity.User;
import com.chatappspringboot.repository.LineChatRepository;
import com.chatappspringboot.repository.RoomRepository;
import com.chatappspringboot.service.LineChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LineChatServiceImpl implements LineChatService {
    @Autowired
    private LineChatRepository lineChatRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public FormGetLineChat save(FormLineChat formLineChat, String saveBy) throws Exception {
        Room room = roomRepository.findById(
                formLineChat.getIdRoom()
        ).orElseThrow(
                () -> new Exception("Can not find room by id = " + formLineChat.getIdRoom())
        );
        User user = userService.findByUsername(saveBy);
        boolean checkUserExists = room.getUsers().stream().anyMatch(e -> e.getAccount()
                .getUsername()
                .equals(saveBy));

        if (!checkUserExists) {
            return null;
        }

        LineChat lineChat = lineChatRepository.save(
                new LineChat(
                        null,
                        formLineChat.getText(),
                        user,
                        new Date(),
                        room
                )
        );

        return new FormGetLineChat(
                lineChat.getId(),
                lineChat.getUser().getId(),
                lineChat.getValue(),
                lineChat.getTimeCreated()
        );
    }

    @Override
    public List<FormGetLineChat> get(Long idRoom) throws Exception {
        Room room = roomRepository.findById(idRoom)
                .orElseThrow(
                        () -> new Exception("Can not find room by id = " + idRoom)
                );

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean checkUserExists = !room.getUsers().stream().filter(
                e -> e.getAccount()
                        .getUsername()
                        .equals(username)
        )
                .toList()
                .isEmpty();

        if (!checkUserExists) {
            return null;
        }

        return lineChatRepository.findAllByRoom(room)
                .stream()
                .map(
                        r -> new FormGetLineChat(
                                r.getId(),
                                r.getUser().getId(),
                                r.getValue(),
                                r.getTimeCreated()
                        )
                ).toList();
    }
}
