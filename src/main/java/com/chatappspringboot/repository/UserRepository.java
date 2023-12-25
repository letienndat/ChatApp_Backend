package com.chatappspringboot.repository;

import com.chatappspringboot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u " +
            "WHERE (CONCAT('', u.id) = :searchTerm " +
            "OR LOWER(u.name) LIKE LOWER(concat('%', :searchTerm, '%')) " +
            "OR LOWER(u.account.username) LIKE LOWER(concat('%', :searchTerm, '%')))")
    List<User> findUsersBySearchTerm(@Param("searchTerm") String searchTerm);

    Optional<User> findByAccountUsername(String username);
}
