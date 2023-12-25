package com.chatappspringboot.service;

import com.chatappspringboot.dto.response.FormGetUser;
import com.chatappspringboot.dto.response.FormLoadUser;
import com.chatappspringboot.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    FormGetUser getByUsername(String username) throws Exception;
    List<FormGetUser> getBySearchTerm(String searchTerm);
    FormGetUser save(String username, String name) throws Exception;
    FormLoadUser load(Long id) throws Exception;
    User findById(Long id) throws Exception;
    User findByUsername(String username) throws Exception;
}
