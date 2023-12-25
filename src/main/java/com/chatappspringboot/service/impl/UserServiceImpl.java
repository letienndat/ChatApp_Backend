package com.chatappspringboot.service.impl;

import com.chatappspringboot.dto.response.FormGetUser;
import com.chatappspringboot.dto.response.FormLoadUser;
import com.chatappspringboot.dto.response.FormRoomHome;
import com.chatappspringboot.entity.Account;
import com.chatappspringboot.entity.CustomUserDetail;
import com.chatappspringboot.entity.User;
import com.chatappspringboot.repository.AccountRepository;
import com.chatappspringboot.repository.UserRepository;
import com.chatappspringboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByAccountUsername(username);

        return user.map(CustomUserDetail::new)
                .orElseThrow(
                        () -> new UsernameNotFoundException("Cannot find user by username: " + username)
                );
    }

    @Override
    public FormGetUser getByUsername(String username) throws Exception {
        User user = userRepository.findByAccountUsername(username)
                .orElseThrow(
                        () -> new Exception("Can not find user by username = " + username)
                );
        return new FormGetUser(user.getId(), user.getName(), user.getAccount().getUsername());
    }

    @Override
    public List<FormGetUser> getBySearchTerm(String searchTerm) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<User> users = userRepository.findUsersBySearchTerm(searchTerm)
                .stream()
                .filter(user -> {
                    if (user.getAccount().getUsername().equals(username)) {
                        return false;
                    }
                    return true;
                }).toList();
        return users.stream().map(
                user -> new FormGetUser(user.getId(), user.getName(), user.getAccount().getUsername())
        ).toList();
    }

    @Override
    public FormGetUser save(String username, String name) throws Exception {
        Account account = accountRepository.findById(username).orElseThrow(() -> new Exception("Can not find account by username = " + username));
        User user = userRepository.save(new User(null, name, account, null, null));
        return new FormGetUser(user.getId(), user.getName(), user.getAccount().getUsername());
    }

    @Override
    public FormLoadUser load(Long id) throws Exception {
        FormLoadUser formLoadUser = new FormLoadUser();
        User user = userRepository.findById(id).orElseThrow(() -> new Exception("Can not find user by id = " + id));
        formLoadUser.setId(user.getId());
        formLoadUser.setUsername(user.getAccount().getUsername());
        formLoadUser.setName(user.getName());

        List<FormRoomHome> formRoomHomes = user.getRooms().stream().map(
                room -> new FormRoomHome(room.getId(), room.getUsers().stream().filter(u -> !u.getId().equals(id)).findFirst().get().getName())
        ).toList();
        formLoadUser.setFormRoomHomes(formRoomHomes);

        return formLoadUser;
    }

    @Override
    public User findById(Long id) throws Exception {
        return userRepository.findById(id).orElseThrow(() -> new Exception("Can not find user by id = " + id));
    }

    @Override
    public User findByUsername(String username) throws Exception {
        return userRepository.findByAccountUsername(username).orElseThrow(() -> new Exception("Can not find user by username = " + username));
    }
}
