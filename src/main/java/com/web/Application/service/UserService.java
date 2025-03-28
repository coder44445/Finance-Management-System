package com.web.Application.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.Application.entity.User;
import com.web.Application.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public User getUser(Long id){

        Optional<User> user =  userRepository.findById(id);
        
        if(user.isPresent()){
            return user.get();
        }else{
            return null;
        }
        
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public Boolean saveNewUser(User user){
        
        User userDetails = userRepository.findByUserName(user.getUserName());

        if(userDetails == null){
            
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;

        }else{

            return false;
        }

    }

    public void deleteUser(Long id){

        userRepository.deleteById(id);
    }

    public User findByUserName(String userName){

        return userRepository.findByUserName(userName);
        
    }
}
