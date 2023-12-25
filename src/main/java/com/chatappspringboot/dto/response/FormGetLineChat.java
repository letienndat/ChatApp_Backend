package com.chatappspringboot.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SortComparator;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FormGetLineChat {
    private Long id;
    private Long idUserCreated;
    private String value;
    private Date timeCreated;
}
