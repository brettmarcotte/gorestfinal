package com.example.gorestfinal.controllers;


import com.example.gorestfinal.models.Post;
import com.example.gorestfinal.repositories.PostRepository;
import com.example.gorestfinal.repositories.TodoRepository;
import com.example.gorestfinal.utils.ApiErrorHandling;
import com.example.gorestfinal.validation.PostValidation;
import com.example.gorestfinal.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping ("/api/ToDo")
public class TodoController<ToDo> {

    @Autowired
    PostRepository ToDoRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAllToDo () {
        try {
            Iterable<ToDo> allToDo = (Iterable<ToDo>) ToDoRepository.findAll();

            return new ResponseEntity<>(allToDo, HttpStatus.OK);

        } catch (Exception e){
            return ApiErrorHandling.genericApiError(e);
        }

    }

    @PostMapping
    public ResponseEntity<?> createPost (@RequestBody ToDo newToDo) {
        try {

            ValidationError errors = PostValidation.validatePost((Post) newToDo, ToDoRepository, false);
            if (errors.hasError()) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, errors.toJSONString());
            }

            Post createPost = ToDoRepository(newToDo);

            return new ResponseEntity<>(createPost, HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            return ApiErrorHandling.customApiError(e.getMessage(), e.getStatusCode());
        } catch (Exception e) {
            return ApiErrorHandling.genericApiError(e);
        }
    }

    private Post ToDoRepository(ToDo newToDo) {
        return null;
    }

}
