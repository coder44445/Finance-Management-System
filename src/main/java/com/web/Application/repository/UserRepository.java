package com.web.Application.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.web.Application.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

    User findByUserName(String userName);

    void deleteByUserName(String userName);

}
