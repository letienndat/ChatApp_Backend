package com.chatappspringboot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormPublicKey {
    private Long id;
    private Long idRoom;
    private Long idUser;
    private String publicKey;
    private String publicKeyOther;
}
