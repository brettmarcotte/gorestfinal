package com.example.gorestfinal.validation;

import com.example.gorestfinal.models.Post;
import com.example.gorestfinal.models.Todo;
import com.example.gorestfinal.repositories.PostRepository;
import com.example.gorestfinal.repositories.TodoRepository;
import com.example.gorestfinal.repositories.UserRepository;

import java.util.Optional;

public class TodoValidation {
    public static ValidationError validateTodo(Todo todo, TodoRepository todoRepo,
                                               boolean isUpdate) {

        ValidationError errors = new ValidationError();

        if (isUpdate) {
            if (todo.getId() == 0) {
                errors.addError("id", "ID can not be left blank");
            } else {
                Optional<Todo> foundUser = todoRepo.findById(todo.getId());
                if (foundUser.isEmpty()) {

                    errors.addError("id", "No user found with the ID: " + todo.getId());
                }
            }
        }


        String todoTitle = todo.getTitle();
        String todoBody = todo.getBody();
        long todoUserId = todo.getUser_id();

        if (todoTitle == null || todoTitle.trim().equals("")) {
            errors.addError("title", "Title can not be left blank");
        }

        if (todoBody == null || todoBody.trim().equals("")) {
            errors.addError("body", "Body can not be left blank");
        }

        if (todoUserId == 0) {
            errors.addError("user_id", "User_ID can not be left blank");
        } else {
            // is the postUserId connected to an existing user.
            
            Optional<Todo> foundTodo = todoRepo.findById(todoUserId);

            if (foundTodo.isEmpty()) {
                errors.addError("user_id", "User_ID is invalid because there is no user found with the id: " + todoUserId);
            }

        }
        return errors;

    }
}
