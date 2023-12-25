package com.chatappspringboot.controller;

import com.chatappspringboot.dto.request.FormGetPublicKey;
import com.chatappspringboot.dto.request.FormPublicKey;
import com.chatappspringboot.dto.response.MessageResponse;
import com.chatappspringboot.dto.response.TypeMessage;
import com.chatappspringboot.service.impl.PublicKeyServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public-key")
public class PublicKeyController {
    @Autowired
    private PublicKeyServiceImpl publicKeyService;

    @PostMapping("/save")
    public ResponseEntity<MessageResponse> save(@Valid @RequestBody FormPublicKey formPublicKey) throws Exception {
        com.chatappspringboot.dto.response.FormPublicKey formPublicKeyRes = publicKeyService.save(formPublicKey);

        return ResponseEntity.ok(new MessageResponse(TypeMessage.SUCCESS, formPublicKeyRes));
    }

    @PostMapping("/get")
    public ResponseEntity<MessageResponse> get(@Valid @RequestBody FormGetPublicKey formGetPublicKey) throws Exception {
        com.chatappspringboot.dto.response.FormPublicKey formPublicKeyRes = publicKeyService.get(formGetPublicKey);

        return ResponseEntity.ok(new MessageResponse(TypeMessage.SUCCESS, formPublicKeyRes));
    }
}
