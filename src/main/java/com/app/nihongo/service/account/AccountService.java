package com.app.nihongo.service.account;


import com.app.nihongo.dao.UserRepository;
import com.app.nihongo.entity.Alert;
import com.app.nihongo.entity.User;
import com.app.nihongo.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
public class AccountService implements IAccountService{
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private EmailService emailService;

    public ResponseEntity<?> dangKyNguoiDung(User user){

        // Kiểm tra tên đăng nhập đã tồn tại chưa?
        if(userRepository.existsByUsername(user.getUsername())){
            return ResponseEntity.badRequest().body(new Alert("Tên đăng nhập đã tồn tại."));
        }

        // Kiểm tra email đã tồn tại chưa?
        if(userRepository.existsByEmail(user.getEmail())){
            return ResponseEntity.badRequest().body(new Alert("Email đã tồn tại."));
        }

        // Mã hóa mật khẩu
        String encryptPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptPassword);

//        user.setAvatar(user.getAvatar());
        // Gán và gửi thông tin kích hoạt
        user.setActiveNumber(taoMaKichHoat());
        user.setActived(false);
        // Lưu người dùng người dùng vào DB
        User nguoiDung_daDangKy = userRepository.save(user);

        // Gửi email cho người dùng để họ kích hoạt
        guiEmailKichHoat(user.getEmail(), user.getActiveNumber());

        return ResponseEntity.ok("Đăng ký thành công");
    }


    private String taoMaKichHoat(){
        // Tạo mã ngẫu nhiên
        return UUID.randomUUID().toString();
    }

    private void guiEmailKichHoat(String email, String activeNumber){
        String subject = "Kích hoạt tài khoản";
        String text = "Vui lòng sử dụng mã sau để kich hoạt cho tài khoản <"+email+">:<html><body><br/><h1>"+activeNumber+"</h1></body></html>";
        text+="<br/> Click vào đường link để kích hoạt tài khoản: ";
        String url = "http://localhost:3000/active/"+email+"/"+activeNumber;
        text+=("<br/> <a href="+url+">"+url+"</a> ");

        emailService.sendMessage("admmin@gmail.com", email, subject, text);
    }

    public ResponseEntity<?> kichHoatTaiKHoan(String email, String activeNumber) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return ResponseEntity.badRequest().body(new Alert("Người dùng không tồn tại!"));
        }

        if (user.isActived()) {
            return ResponseEntity.badRequest().body(new Alert("Tài khoản đã được kích hoạt!"));
        }

        if (activeNumber.equals(user.getActiveNumber())) {
            user.setActived(true);
            userRepository.save(user);
            return ResponseEntity.ok("Kích hoạt tài khoản thành công!");
        } else {
            return ResponseEntity.badRequest().body(new Alert("Mã kích hoạt không chính xác!"));
        }
    }
}
