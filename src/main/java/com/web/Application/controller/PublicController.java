package com.web.Application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.web.Application.entity.User;
import com.web.Application.service.UserDetailsServiceImpl;
import com.web.Application.service.UserService;
import com.web.Application.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/public")
@Slf4j
public class PublicController {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/signup")
    ResponseEntity<?> signup(@RequestBody User user){

        user.setSpendingAltert(true);

        if(user.getBudget() == 0.0){

            return new ResponseEntity<>("User must have budget greater than",HttpStatus.NOT_ACCEPTABLE);
        }

        boolean isSaved = userService.saveNewUser(user);

        if(isSaved){
            
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>("User name already exists",HttpStatus.CONFLICT);
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
         }catch (Exception e){
            log.error("Exception occurred while createAuthenticationToken ", e);
            return new ResponseEntity<>("Incorrect username or password", HttpStatus.BAD_REQUEST);
        }
    }


}
