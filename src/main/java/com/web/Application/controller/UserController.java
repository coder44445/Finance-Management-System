package com.web.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.Application.entity.User;
import com.web.Application.repository.UserRepository;
import com.web.Application.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping("/profile")
    ResponseEntity<?> getProflie(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByUserName(authentication.getName());

        
        if(user == null){
            return new ResponseEntity<>("User not found",HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @PutMapping
    ResponseEntity<?> updateUserDetails(@RequestBody User user){
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User userDetails = userRepository.findByUserName(authentication.getName());

        if(userDetails == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userDetails.setEmail(user.getEmail());
        userDetails.setUserName(user.getUserName());
        userDetails.setSpendingAltert(user.getSpendingAltert());

        // checking if the password need to be updated

        String password = passwordEncoder.encode(user.getPassword()); // endcode the password 

        if( !password.equals(userDetails.getPassword()) ){ // update if hash is not equal

            userDetails.setPassword(passwordEncoder.encode(user.getPassword()));
        }


        userService.saveUser(userDetails);

        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping
    ResponseEntity<?> deleteUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        userRepository.deleteByUserName(authentication.getName());

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
