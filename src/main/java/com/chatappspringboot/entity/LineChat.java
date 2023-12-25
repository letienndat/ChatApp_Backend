package com.chatappspringboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "line_chats")
public class LineChat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String value;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @NotNull
    @Column(name = "time_created")
    private Date timeCreated;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Room room;
}
