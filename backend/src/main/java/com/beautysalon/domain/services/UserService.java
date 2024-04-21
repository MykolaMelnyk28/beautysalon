package com.beautysalon.domain.services;

import com.beautysalon.domain.entities.UserEntity;
import com.beautysalon.domain.repository.UserRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserEntity> getAll() {
        List<UserEntity> users = userRepository.findAll();
        Pageable pageable = PageRequest.of(0, 15, Sort.by(Sort.Order.asc("id")));
        return new PageImpl<>(users, pageable, users.size());
    }

}
