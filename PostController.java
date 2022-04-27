package com.example.gorestfinal.controllers;


import com.example.gorestfinal.models.Post;
import com.example.gorestfinal.repositories.PostRepository;
import com.example.gorestfinal.utils.ApiErrorHandling;
import com.example.gorestfinal.validation.PostValidation;
import com.example.gorestfinal.validation.ValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping ("/api/posts")
public class PostController {

    @Autowired
    PostRepository postRepository;

//    @GetMapping ("/test")
//    public String testRoute () {
//        return "TESTING!";
//    }


    /*

  Required Routes for GoRestSQL Final: complete for each resource; User, Post, Comment, Todo,

       * GET route that returns one [resource] by ID from the SQL database
       * GET route that returns all [resource]s stored in the SQL database
       * DELETE route that deletes one [resource] by ID from SQL database (returns the deleted SQL [resource] data)
       * DELETE route that deletes all [resource]s from SQL database (returns how many [resource]s were deleted)
       * POST route that queries one [resource] by ID from GoREST and saves their data to your local database (returns
       the SQL [resource] data)
       *POST route that uploads all [resource]s from the GoREST API into the SQL database (returns how many
       [resource]s were uploaded)
       *POST route that create a [resource] on JUST the SQL database (returns the newly created SQL [resource] data)
       *PUT route that updates a [resource] on JUST the SQL database (returns the updated SQL [resource] data)
* */


    @GetMapping ("/all")
    public ResponseEntity<?> getAllPosts () {
        try {
            Iterable<Post> allPosts = postRepository.findAll();

            return new ResponseEntity<>(allPosts, HttpStatus.OK);

        } catch (Exception e){
            return ApiErrorHandling.genericApiError(e);
        }

    }

    @PostMapping
    public ResponseEntity<?> createPost (@RequestBody Post newPost) {
        try {

            ValidationError errors = PostValidation.validatePost(newPost, postRepository, false);
            if (errors.hasError()) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, errors.toJSONString());
            }

            Post createPost = postRepository.save(newPost);

            return new ResponseEntity<>(createPost, HttpStatus.CREATED);
        } catch (HttpClientErrorException e) {
            return ApiErrorHandling.customApiError(e.getMessage(), e.getStatusCode());
        } catch (Exception e) {
            return ApiErrorHandling.genericApiError(e);
        }
    }

}
