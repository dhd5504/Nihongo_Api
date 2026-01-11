package com.app.nihongo.service.account;


import com.app.nihongo.entity.User;
import org.springframework.http.ResponseEntity;

public interface IAccountService {
    ResponseEntity<?> dangKyNguoiDung(User user);
    ResponseEntity<?> kichHoatTaiKHoan(String email, String activeNumber);
}
