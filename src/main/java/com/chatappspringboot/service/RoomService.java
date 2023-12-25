package com.chatappspringboot.service;

import com.chatappspringboot.dto.response.FormGetRoom;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoomService {
    FormGetRoom get(Long idUser, Long idUserOther) throws Exception;
    FormGetRoom get(Long id) throws Exception;
//    List<FormGetRoom> gets(Long idUser) throws Exception;
}
