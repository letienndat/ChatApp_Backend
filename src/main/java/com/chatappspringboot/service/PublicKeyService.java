package com.chatappspringboot.service;

import com.chatappspringboot.dto.request.FormGetPublicKey;
import com.chatappspringboot.dto.response.FormPublicKey;
import org.springframework.stereotype.Service;

@Service
public interface PublicKeyService {
    FormPublicKey save(com.chatappspringboot.dto.request.FormPublicKey formPublicKey) throws Exception;
    FormPublicKey get(FormGetPublicKey formPublicKey) throws Exception;
}
