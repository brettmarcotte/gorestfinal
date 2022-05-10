package com.example.gorestfinal.validation;

import com.example.gorestfinal.models.Post;
import com.example.gorestfinal.models.User;
import com.example.gorestfinal.repositories.PostRepository;
import com.example.gorestfinal.repositories.UserRepository;

import java.util.Optional;

public class UserValidation {

    public static ValidationError validateUser(User user, UserRepository userRepo,
                                               boolean isUpdate) {

        ValidationError errors = new ValidationError();

        if (isUpdate) {
            if (user.getId() == 0) {
                errors.addError("id", "ID can not be left blank");
            } else {
                Optional<User> foundUser = userRepo.findById(user.getId());
                if (foundUser.isEmpty()) {
                    errors.addError("id", "No user found with the ID: " + user.getId());
                }
            }
        }

        String userTitle = user.getTitle();
        String userBody = user.getBody();
        long userUserId = user.getUser_id();

        if (userTitle == null || userTitle.trim().equals("")) {
            errors.addError("title", "Title can not be left blank");
        }

        if (userBody == null || userBody.trim().equals("")) {
            errors.addError("body", "Body can not be left blank");
        }

        if (userUserId == 0) {
            errors.addError("user_id", "User_ID can not be left blank");
        } else {
            // is the postUserId connected to an existing user.
            Optional<User> foundUser = userRepo.findById(userUserId);

            if (foundUser.isEmpty()) {
                errors.addError("user_id", "User_ID is invalid because there is no user found with the id: " + userUserId);
            }

        }
        return errors;

    }

    public static ValidationError validateUser(Object newUser, UserRepository userRepository, boolean b) {
   return null;
    }
}
