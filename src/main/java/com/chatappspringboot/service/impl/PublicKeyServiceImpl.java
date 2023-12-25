package com.chatappspringboot.service.impl;

import com.chatappspringboot.dto.request.FormGetPublicKey;
import com.chatappspringboot.dto.response.FormPublicKey;
import com.chatappspringboot.entity.PublicKey;
import com.chatappspringboot.entity.Room;
import com.chatappspringboot.repository.PublicKeyRepository;
import com.chatappspringboot.repository.RoomRepository;
import com.chatappspringboot.service.PublicKeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PublicKeyServiceImpl implements PublicKeyService {
    @Autowired
    private PublicKeyRepository publicKeyRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserServiceImpl userService;

    @Override
    public FormPublicKey save(com.chatappspringboot.dto.request.FormPublicKey formPublicKey) throws Exception {
        Room room = roomRepository.findById(
                formPublicKey.getIdRoom()
        ).orElseThrow(
                () -> new Exception("Can not find room by id = " + formPublicKey.getIdRoom())
        );

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long id = userService.getByUsername(username).getId();

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

        PublicKey publicKey = publicKeyRepository.findPublicKeyByRoom(room);
        if (publicKey == null) {
            publicKey = new PublicKey();
            publicKey.setRoom(room);
        }

        if (publicKey.getIdUserFirst() == null || id.equals(publicKey.getIdUserFirst())) {
            if (publicKey.getIdUserFirst() == null) {
                publicKey.setIdUserFirst(id);
            }
            publicKey.setPublicKeyUserFirst(formPublicKey.getPublicKey());
        } else if (publicKey.getIdUserSecond() == null || id.equals(publicKey.getIdUserSecond())) {
            if (publicKey.getIdUserSecond() == null) {
                publicKey.setIdUserSecond(id);
            }
            publicKey.setPublicKeyUserSecond(formPublicKey.getPublicKey());
        }
        publicKeyRepository.save(publicKey);

        return new FormPublicKey(publicKey.getId(), formPublicKey.getIdRoom(), id, formPublicKey.getPublicKey(), null);
    }

    @Override
    public FormPublicKey get(FormGetPublicKey formGetPublicKey) throws Exception {
        Room room = roomRepository.findById(
                formGetPublicKey.getIdRoom()
        ).orElseThrow(
                () -> new Exception("Can not find room by id = " + formGetPublicKey.getIdRoom())
        );

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long id = userService.getByUsername(username).getId();

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

        PublicKey publicKey = publicKeyRepository.findPublicKeyByRoom(room);
        FormPublicKey formPublicKeyRes = new FormPublicKey(
                publicKey.getId(),
                formGetPublicKey.getIdRoom(),
                null,
                null,
                null
        );

        Long idUser = formGetPublicKey.getIdUser();
        formPublicKeyRes.setIdUser(idUser);

        if (idUser.equals(publicKey.getIdUserFirst())) {
            formPublicKeyRes.setPublicKey(publicKey.getPublicKeyUserFirst());
            formPublicKeyRes.setPublicKeyOther(publicKey.getPublicKeyUserSecond());
        } else if (idUser.equals(publicKey.getIdUserSecond())) {
            formPublicKeyRes.setPublicKey(publicKey.getPublicKeyUserSecond());
            formPublicKeyRes.setPublicKeyOther(publicKey.getPublicKeyUserFirst());
        }

        return formPublicKeyRes;
    }
}
