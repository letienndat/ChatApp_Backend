package com.chatappspringboot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormGetRoom {
    private Long id;
    private Integer numberMember;
    private Collection<FormGetUser> users;
    private Collection<FormGetLineChat> lineChats;
}
