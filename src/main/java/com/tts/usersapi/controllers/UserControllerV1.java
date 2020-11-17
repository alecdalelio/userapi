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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class UserControllerV1 {
   
    @Autowired
    UserRepository userRepository;

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

    // Localhost:8080/users/8
    @GetMapping("/user/{id}")
    public ResponseEntity<Optional<UserV2>> getUser(@PathVariable(value="id") Long id){

        Optional<UserV2> user = userRepository.findById(id);

        if(!user.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);

    }

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody @Valid UserV2 user, BindingResult bindingResult){
        System.out.println("Binding Result --- : " + bindingResult);

        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
    }

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

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
