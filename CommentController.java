package com.example.gorestfinal.controllers;


import com.example.gorestfinal.models.Comment;
import com.example.gorestfinal.models.Post;
import com.example.gorestfinal.repositories.PostRepository;
import com.example.gorestfinal.utils.ApiErrorHandling;
import com.example.gorestfinal.validation.CommentValidation;
import com.example.gorestfinal.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping ("api/comments")
public class CommentController {

    @Autowired
    PostRepository commentRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAllPosts () {
        try {
            Iterable<Post> allComments = commentRepository.findAll();

            return new ResponseEntity<>(allComments, HttpStatus.OK);

        } catch (Exception e){
            return ApiErrorHandling.genericApiError(e);
        }

    }

    @PostMapping
    public ResponseEntity<?> createComment (@RequestBody Comment newComment) {
        try {

            ValidationError errors = CommentValidation.validatePost(newComment, commentRepository, false);
            if (errors.hasError()) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, errors.toJSONString());
            }

            Comment createComment = (Comment) commentRepository;

            return new ResponseEntity<>(createComment, HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            return ApiErrorHandling.customApiError(e.getMessage(), e.getStatusCode());
        } catch (Exception e) {
            return ApiErrorHandling.genericApiError(e);
        }
    }
    
}
