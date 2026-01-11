package com.app.nihongo.service.user;

import com.app.nihongo.dto.UserUpdateDTO;
import com.app.nihongo.dto.UserExpDTO;
import com.app.nihongo.dto.UserDTO;
import com.app.nihongo.dto.UserInfoDTO;
import com.app.nihongo.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService extends UserDetailsService {
    public User findByEmail(String email);
    public User findByUsername(String username);
    public ResponseEntity<?> delete(int id);
    ResponseEntity<?> saveUser(UserDTO userDTO);
    ResponseEntity<UserDTO> getUserById(int id);
    ResponseEntity<?> updateUser(int id, UserDTO userDTO);
    ResponseEntity<?> updateUserInfo(UserUpdateDTO userUpdateDTO);
    Integer getUserExperience(Integer userId);
    List<UserExpDTO> getUserExpByLevel(String level);

    ResponseEntity<UserInfoDTO> getUserInfoById(Integer userId);
}