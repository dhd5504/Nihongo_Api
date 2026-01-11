package com.app.nihongo.mapper;

import com.app.nihongo.dto.UserDTO;
import com.app.nihongo.entity.User;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserMapper {
    private BCryptPasswordEncoder passwordEncoder =new BCryptPasswordEncoder();



    public User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        String encryptPassword = passwordEncoder.encode(userDTO.getPassword());
        user.setPassword(encryptPassword);

        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setDateOfBirth(userDTO.getDateOfBirth());
        user.setAddress(userDTO.getAddress());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setGender(userDTO.getGender());
        user.setActiveNumber(userDTO.getActiveNumber());
        user.setActived(userDTO.isActived());
        return user;
    }

    public UserDTO toDto(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setAddress(user.getAddress());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setGender(user.getGender());
        userDTO.setActiveNumber(user.getActiveNumber());
        userDTO.setActived(user.isActived());
        return userDTO;
    }
}
