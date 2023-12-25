package com.chatappspringboot.repository;

import com.chatappspringboot.entity.Room;
import com.chatappspringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END FROM Room r " +
            "JOIN r.users u1 " +
            "JOIN r.users u2 " +
            "WHERE u1 = :user1 AND u2 = :user2")
    boolean existsRoomWithUsers(@Param("user1") User user1, @Param("user2") User user2);

    @Query("SELECT r FROM Room r " +
            "WHERE :user1 MEMBER OF r.users " +
            "AND :user2 MEMBER OF r.users")
    Room findRoomByUsers(@Param("user1") User user1, @Param("user2") User user2);
}
