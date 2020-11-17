package com.tts.usersapi.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.tts.usersapi.models.UserV2;
import com.tts.usersapi.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

@RestController
@Api(value="users", description="Operations pertaining to users")
public class UserControllerV2 {
   
    @Autowired
    UserRepository userRepository;

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved users data"),
        @ApiResponse(code = 401, message = "You are not authorized to view the user data")
    })
    @ApiOperation(value = "Get all users", response = UserV2.class, responseContainer = "List")
    @GetMapping("/users")
    public ResponseEntity<List<UserV2>> getUsers(@RequestParam (value = "state", required=false) String state) {

        UserV2 user = new UserV2("a", "b", "c");
        userRepository.save(user);

        // localhost:8080/users?state=Ohio
        // state=Ohio is a request parameter
        // will only display people from Ohio
        System.out.println(state);

        if(state != null){
            List<UserV2> filteredUserList = userRepository.findByState(state);
            return new ResponseEntity<List<UserV2>>(filteredUserList, HttpStatus.OK);
        } else {
            Iterable<UserV2> userIterable = userRepository.findAll();
            
            List<UserV2> userList = (List<UserV2>) userIterable;

            // int myInt = 8;
            // double myDub = (double) myInt;

            return new ResponseEntity<List<UserV2>>(userList, HttpStatus.OK);
        }
    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved user's data"),
        @ApiResponse(code = 401, message = "You are not authorized to view this user's data")
    })
    @ApiOperation(value = "Get a specific user", response = UserV2.class, responseContainer = "User")
    // Localhost:8080/users/8
    @GetMapping("/user/{id}")
    public ResponseEntity<Optional<UserV2>> getUser(@PathVariable(value="id") Long id){

        Optional<UserV2> user = userRepository.findById(id);

        if(!user.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successfully created new user"),
        @ApiResponse(code = 400, message = "You are not authorized to create new user")
    })
    @ApiOperation(value = "Create a new user", response = UserV2.class, responseContainer = "User")
    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserV2 user, BindingResult bindingResult){
        System.out.println("Binding Result --- : " + bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successfully edited user"),
        @ApiResponse(code = 400, message = "Bad request: could not find user data"),
        @ApiResponse(code = 404, message = "User could not be edited because it could not be found")
    })
    @ApiOperation(value = "Edit a user", response = UserV2.class, responseContainer = "User")
    @PutMapping("/users/{id}")
    public ResponseEntity<Void> editUser(@PathVariable(value="id") Long id, @RequestBody @Valid UserV2 user, BindingResult bindingResult) {

        if(userRepository.findById(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        
        Optional<UserV2> requestedUser = userRepository.findById(id);
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully deleted user")
    })
    @ApiOperation(value = "Delete all users", response = UserV2.class, responseContainer = "List")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
