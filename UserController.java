package com.careerdevs.gorestsqlv3.Controllers;

import com.careerdevs.gorestsqlv3.Repos.UserRepository;
import com.careerdevs.gorestsqlv3.Models.User;
import com.careerdevs.gorestsqlv3.Utils.ApiErrorHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

//    Required Routes for GoRestSQL MVP:
//    GET route that returns one user by ID from the SQL database
//    GET route that returns all users stored in the SQL database
//    DELETE route that deletes one user by ID from SQL database (returns the deleted SQL user data)
//    DELETE route that deletes all users from SQL database (returns how many users were deleted)
//    POST route that queries one user by ID from GoREST and saves their data to your local database (returns the SQL user data)
//    POST route that uploads all users from the GoREST API into the SQL database (returns how many users were uploaded)
//    POST route that create a user on JUST the SQL database (returns the newly created SQL user data)
//    PUT route that updates a user on JUST the SQL database (returns the updated SQL user data)

    @Autowired
    private UserRepository userRepository;

    @GetMapping ("/upload/{id}")
    public ResponseEntity<String> getUserById (
            @PathVariable ("id") String userId,
            RestTemplate restTemplate
    ) {
        try {
            int uID = Integer.parseInt(userId);
            //check the range

            String url = "https://gorest.co.in/public/v2/users/" + uID;

            User foundUser = restTemplate.getForObject(url, User.class);

            System.out.println(foundUser);

            return new ResponseEntity<>("temp", HttpStatus.OK);

        } catch (NumberFormatException e) {

            return new ResponseEntity<>("ID must be a number", HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getClass());

            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    //https:localhost://8080/user/all
    @GetMapping("/all")
    public ResponseEntity<?> getAllUser () {
        try {
            Iterable<User> allUsers = userRepository.findAll();
            return new ResponseEntity<>(allUsers, HttpStatus.OK);

        } catch ( Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getClass());

            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping
    public ResponseEntity<?> deleteUserById (@PathVariable ("id") String id) {
        try {

            if (ApiErrorHandling.isStrNaN(id)) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, id + " is not a valid ID");
           }
            int uID = Integer.parseInt(id);

            Optional<User> foundUser = userRepository.findById(uID);

            if (foundUser.isEmpty()) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, " User Not Found With ID; " + id);

            }

            userRepository.deleteById(uID);

            return new ResponseEntity<>(foundUser, HttpStatus.OK);

        } catch (HttpClientErrorException e) {
            return null;
        }
    }

    @PostMapping ()
    public ResponseEntity<?> uploadUserById ( @PathVariable ) {


        return null;
    }

}

