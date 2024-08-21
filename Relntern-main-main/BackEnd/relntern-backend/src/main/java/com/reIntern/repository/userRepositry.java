package com.reIntern.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reIntern.model.user;

public interface userRepositry extends JpaRepository<user,Integer> {


    // Method to find user by username (email)
    user findByUsername(String username);

    // Method to find user by username and password
    user findByUsernameAndPassword(String username, String password);

}
