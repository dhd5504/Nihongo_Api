package com.app.nihongo.service.user;

import com.app.nihongo.dao.UserRepository;
import com.app.nihongo.dto.UserExpDTO;
import com.app.nihongo.dto.UserDTO;
import com.app.nihongo.dto.UserInfoDTO;
import com.app.nihongo.dto.UserUpdateDTO;
import com.app.nihongo.entity.User;
import com.app.nihongo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private com.app.nihongo.dao.UserProgressRepository userProgressRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepositor) {
        this.userRepository = userRepository;

    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findByUsername(String userName) {
        return userRepository.findByUsername(userName);
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = findByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("Tài khoản không tồn tại!");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                AuthorityUtils.NO_AUTHORITIES);

    }

    @Override
    public ResponseEntity<?> delete(int id) {
        try {

            User user = userRepository.findByUserId(id);

            userRepository.deleteById(id);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("thành công");
    }

    @Override
    public ResponseEntity<?> saveUser(UserDTO userDTO) {
        try {
            User user = userMapper.toEntity(userDTO);
            userRepository.save(user);
            return ResponseEntity.ok("User created successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to create user");
        }
    }

    @Override
    public ResponseEntity<UserDTO> getUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            UserDTO userDTO = userMapper.toDto(user.get());
            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> updateUser(int id, UserDTO userDTO) {
        try {
            Optional<User> existingUserOptional = userRepository.findById(id);
            User user = userMapper.toEntity(userDTO);
            if (existingUserOptional.isPresent()) {

                user.setUserId(id);
                userRepository.save(user);
                return ResponseEntity.ok("User updated successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Failed to update user");
        }
    }

    @Override
    public Integer getUserExperience(Integer userId) {
        return userProgressRepository.sumScoreByUserId(userId);
    }

    @Override
    public List<UserExpDTO> getUserExpByLevel(String level) {
        return userRepository.findUserExpByLevel(level);
    }

    @Override
    public ResponseEntity<UserInfoDTO> getUserInfoById(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Integer userXP = getUserExperience(userId);
            String name = (user.getFirstName() != null ? user.getFirstName() : "") + " " +
                    (user.getLastName() != null ? user.getLastName() : "");
            String phone = user.getPhoneNumber();
            String email = user.getEmail();
            String avatar = user.getAvatar();

            UserInfoDTO userInfo = new UserInfoDTO(name.trim(), userXP, phone, email, avatar);

            return ResponseEntity.ok(userInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> updateUserInfo(UserUpdateDTO userUpdateDTO) {

        Optional<User> existingUserOptional = userRepository.findById(userUpdateDTO.getUserId());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!existingUserOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        User user = existingUserOptional.get();
        if (!userUpdateDTO.getName().equals("")) {
            user.setFirstName(userUpdateDTO.getName());

        }
        if (userUpdateDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        }
        if (!userUpdateDTO.getPassword().equals("")) {
            String encryptedPassword = passwordEncoder.encode(userUpdateDTO.getPassword());
            user.setPassword(encryptedPassword);
        }
        if (userUpdateDTO.getAvatar() != null) {
            user.setAvatar(userUpdateDTO.getAvatar());
        }

        userRepository.save(user);

        return ResponseEntity.ok("User infor updated successfully");
    }

}
