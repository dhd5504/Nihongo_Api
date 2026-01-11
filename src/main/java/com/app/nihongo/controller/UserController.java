package com.app.nihongo.controller;

import com.app.nihongo.dao.UserRepository;
import com.app.nihongo.dto.UserExpDTO;
import com.app.nihongo.dto.UserInfoDTO;
import com.app.nihongo.dto.UserProgressDTO;
import com.app.nihongo.dto.UserUpdateDTO;
import com.app.nihongo.service.unit.UnitService;
import com.app.nihongo.service.user.UserService;
import com.app.nihongo.service.userprogress.UserProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UnitService unitService;

    @GetMapping("/experience/{userId}")
    public ResponseEntity<?> getExperience(@PathVariable Integer userId) {
        Integer exp = userService.getUserExperience(userId);
        return ResponseEntity.ok(exp);
    }
    @GetMapping("/experience-by-level")
    public ResponseEntity<List<UserExpDTO>> getUserExperienceByLevel(@RequestParam String level) {
        List<UserExpDTO> expList = userService.getUserExpByLevel(level);
        return ResponseEntity.ok(expList);
    }
    @GetMapping("/info/{userId}")
    public ResponseEntity<UserInfoDTO> getUserInfo(@PathVariable Integer userId) {
        return userService.getUserInfoById(userId);
    }
    @PreAuthorize("#userUpdateDTO.userId == authentication.principal.id")
    @PutMapping("/update-info")
    public ResponseEntity<?> updateUserInfo(@RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.updateUserInfo(userUpdateDTO);
    }

    @GetMapping("/check-new-user/{userId}")
    public boolean checkUserLessonStatus(@PathVariable Integer userId) {
        return unitService.userLessonStatusExist(userId);
    }
}
