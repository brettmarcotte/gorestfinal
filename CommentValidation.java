package com.example.gorestfinal.validation;

import com.example.gorestfinal.models.Comment;
import com.example.gorestfinal.models.Post;
import com.example.gorestfinal.repositories.CommentRepository;
import com.example.gorestfinal.repositories.PostRepository;

import java.util.Optional;

public class CommentValidation {
    public static ValidationError validateComment(Comment comment, CommentRepository commentRepo,
                                               boolean isUpdate) {

        ValidationError errors = new ValidationError();

        if (isUpdate) {
            if (comment.getId() == 0) {
                errors.addError("id", "ID can not be left blank");
            } else {
                Optional<Comment> foundUser = commentRepo.findById(comment.getId());
                if (foundUser.isEmpty()) {
                    errors.addError("id", "No user found with the ID: " + comment.getId());
                }
            }
        }


        String commentTitle = comment.getTitle();
        String commentBody = comment.getBody();
        long commentUserId = comment.getUser_id();

        if (commentTitle == null || commentTitle.trim().equals("")) {
            errors.addError("title", "Title can not be left blank");
        }

        if (commentBody == null || commentBody.trim().equals("")) {
            errors.addError("body", "Body can not be left blank");
        }

        if (commentUserId == 0) {
            errors.addError("user_id", "User_ID can not be left blank");
        } else {
            // is the postUserId connected to an existing user.
            Optional<Comment> foundUser = commentRepo.findById(commentUserId);

            if (foundUser.isEmpty()) {
                errors.addError("user_id", "User_ID is invalid because there is no user found with the id: " + commentUserId);
            }

        }
        return errors;

    }
    public static ValidationError validatePost(Comment newComment, PostRepository commentRepository, boolean b) {
        return null;
    }
}
