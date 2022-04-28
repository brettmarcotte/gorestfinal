package com.example.gorestfinal.repositories;

import com.example.gorestfinal.models.Post;
import com.example.gorestfinal.models.User;

import java.util.Optional;

public interface UserRepository {
    static Iterable<User> findAll() {
        return null;
    }

    static Post save(Post newPost) {
        return null;
    }

    static User save(User newUser) {
        return null;
    }

    Optional<User> findById(long postUserId);
}
