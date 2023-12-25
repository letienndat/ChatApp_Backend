package com.chatappspringboot.service;

import com.chatappspringboot.dto.request.FormLineChat;
import com.chatappspringboot.dto.response.FormGetLineChat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LineChatService {
    FormGetLineChat save(FormLineChat formLineChat, String saveBy) throws Exception;
    List<FormGetLineChat> get(Long idRoom) throws Exception;
}
