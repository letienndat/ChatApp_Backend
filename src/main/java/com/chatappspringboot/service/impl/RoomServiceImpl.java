package com.chatappspringboot.service.impl;

import com.chatappspringboot.dto.response.FormGetLineChat;
import com.chatappspringboot.dto.response.FormGetRoom;
import com.chatappspringboot.dto.response.FormGetUser;
import com.chatappspringboot.entity.LineChat;
import com.chatappspringboot.entity.Room;
import com.chatappspringboot.entity.User;
import com.chatappspringboot.repository.RoomRepository;
import com.chatappspringboot.repository.UserRepository;
import com.chatappspringboot.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public FormGetRoom get(Long idUser, Long idUserOther) throws Exception {
        User userFirst = userRepository.findById(idUser).orElseThrow(() -> new Exception("Can not find user by id = " + idUser));
        User userSecond = userRepository.findById(idUserOther).orElseThrow(() -> new Exception("Can not find user by id = " + idUserOther));
        boolean checkRoomIsExists = roomRepository.existsRoomWithUsers(userFirst, userSecond);
        FormGetRoom formGetRoom = new FormGetRoom();
        if (checkRoomIsExists) {
            Room room = roomRepository.findRoomByUsers(userFirst, userSecond);
            formGetRoom.setId(room.getId());
            formGetRoom.setNumberMember(room.getNumberMember());

            List<FormGetUser> users = room.getUsers().stream().map(
                    user -> new FormGetUser(user.getId(), user.getName(), user.getAccount().getUsername())
            ).toList();
            formGetRoom.setUsers(users);

            List<FormGetLineChat> lineChats = null;
            if (!room.getLineChats().isEmpty()) {
                lineChats = room.getLineChats().stream().map(
                        lineChat -> new FormGetLineChat(lineChat.getId(), lineChat.getUser().getId(), lineChat.getValue(), lineChat.getTimeCreated())
                ).toList();
            }

//            formGetRoom.setLineChats(lineChats);
            formGetRoom.setLineChats(null);
        } else {
            Collection<User> users = List.of(userFirst, userSecond);
            Room room = roomRepository.save(new Room(null, 2, users, null));
            formGetRoom.setId(room.getId());
            formGetRoom.setNumberMember(room.getNumberMember());

            List<FormGetUser> _users = room.getUsers().stream().map(
                    user -> new FormGetUser(user.getId(), user.getName(), user.getAccount().getUsername())
            ).toList();
            formGetRoom.setUsers(_users);

            formGetRoom.setLineChats(null);
        }
        return formGetRoom;
    }

    @Override
    public FormGetRoom get(Long id) throws Exception {
        FormGetRoom formGetRoom = new FormGetRoom();
        Room room = roomRepository.findById(id).orElseThrow(() -> new Exception("Can not find room by id = " + id));

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

        formGetRoom.setId(room.getId());
        formGetRoom.setNumberMember(room.getNumberMember());

        List<FormGetUser> users = room.getUsers().stream().map(
                user -> new FormGetUser(user.getId(), user.getName(), user.getAccount().getUsername())
        ).toList();
        formGetRoom.setUsers(users);

        Collection<LineChat> sortLineChats = room.getLineChats();

        List<LineChat> _sortLineChats = sortLineChats.stream()
                .sorted(((o1, o2) -> (int) (o1.getId() - o2.getId())))
                .toList();

        List<FormGetLineChat> lineChats = _sortLineChats.stream().map(
                lineChat -> new FormGetLineChat(
                        lineChat.getId(),
                        lineChat.getUser().getId(),
                        lineChat.getValue(),
                        lineChat.getTimeCreated()
                )
        ).toList();

//        formGetRoom.setLineChats(lineChats);
        formGetRoom.setLineChats(null);

        return formGetRoom;
    }

//    @Override
//    public List<FormGetRoom> gets(Long idUser) throws Exception {
//        User user = userRepository.findById(idUser).orElseThrow(() -> new Exception("Can not find user by id = " + idUser));
//        List<FormGetRoom> formGetRooms = user.getRooms().stream().map(
//                room -> {
//                    FormGetRoom formGetRoom = new FormGetRoom();
//                    formGetRoom.setId(room.getId());
//                    formGetRoom.setNumberMember(room.getNumberMember());
//
//                    List<FormGetUser> users = room.getUsers().stream().map(
//                            _user -> new FormGetUser(_user.getId(), _user.getName(), _user.getAccount().getUsername())
//                    ).toList();
//                    formGetRoom.setUsers(users);
//
//                    List<FormGetLineChat> lineChats = room.getLineChats().stream().map(
//                            lineChat -> new FormGetLineChat(lineChat.getId(), lineChat.getValue(), lineChat.getTimeCreated())
//                    ).toList();
//                    formGetRoom.setLineChats(lineChats);
//
//                    return formGetRoom;
//                }
//        ).toList();
//
//        return formGetRooms;
//    }
}
