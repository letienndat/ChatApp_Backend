package com.chatappspringboot.repository;

import com.chatappspringboot.entity.LineChat;
import com.chatappspringboot.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineChatRepository extends JpaRepository<LineChat, Long> {
    List<LineChat> findAllByRoom(Room room);
}
