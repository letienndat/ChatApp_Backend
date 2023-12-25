package com.chatappspringboot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "public_keys")
public class PublicKey implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_user_first")
    private Long idUserFirst;

    @Column(name = "e_user_first")
    private String publicKeyUserFirst;

    @Column(name = "id_user_second")
    private Long idUserSecond;

    @Column(name = "e_user_second")
    private String publicKeyUserSecond;

    @OneToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
