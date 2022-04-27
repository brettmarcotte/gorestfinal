package com.example.gorestfinal.repositories;

import com.example.gorestfinal.models.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findById(long postUserId);
}
