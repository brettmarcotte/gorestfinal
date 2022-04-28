package com.example.gorestfinal.controllers;


import com.example.gorestfinal.models.Post;
import com.example.gorestfinal.models.User;
import com.example.gorestfinal.repositories.UserRepository;
import com.example.gorestfinal.utils.ApiErrorHandling;
import com.example.gorestfinal.validation.UserValidation;
import com.example.gorestfinal.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping ("/api/User")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers () {
        try {
            Iterable<User> allUsers = UserRepository.findAll();

            return new ResponseEntity<>(allUsers, HttpStatus.OK);

        } catch (Exception e){
            return ApiErrorHandling.genericApiError(e);
        }

    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User newUser) {
        try {

            ValidationError errors = UserValidation.validateUser(newUser, userRepository, false);
            if (errors.hasError()) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, errors.toJSONString());
            }

            User createUser = UserRepository.save(newUser);

            return new ResponseEntity<>(createUser, HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            return ApiErrorHandling.customApiError(e.getMessage(), e.getStatusCode());
        } catch (Exception e) {
            return ApiErrorHandling.genericApiError(e);
        }
    }


}
