package com.chatappspringboot.repository;

import com.chatappspringboot.entity.PublicKey;
import com.chatappspringboot.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicKeyRepository extends JpaRepository<PublicKey, Long> {
    PublicKey findPublicKeyByRoom(Room room);
}
