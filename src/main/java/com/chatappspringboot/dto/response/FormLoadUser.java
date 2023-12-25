package com.chatappspringboot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormLoadUser {
    private Long id;
    private String name;
    private String username;
    private List<FormRoomHome> formRoomHomes;
}
