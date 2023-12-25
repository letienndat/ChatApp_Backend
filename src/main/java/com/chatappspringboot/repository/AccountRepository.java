package com.chatappspringboot.repository;

import com.chatappspringboot.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM Account a " +
            "WHERE a.username = :username " +
            "AND a.password = :password")
    boolean existsAccount(@Param("username") String username, @Param("password") String password);

    boolean existsByUsername(String username);
}
