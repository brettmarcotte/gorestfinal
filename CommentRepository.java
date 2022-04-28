package com.example.gorestfinal.repositories;

import com.example.gorestfinal.models.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository <Comment, Long> {
    Iterable<Comment> findAll();

}
