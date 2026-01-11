package com.app.nihongo.controller;

import com.app.nihongo.dao.UserRepository;
import com.app.nihongo.entity.User;
import com.app.nihongo.security.JwtResponse;
import com.app.nihongo.security.LoginRequest;
import com.app.nihongo.service.account.IAccountService;
import com.app.nihongo.service.jwt.JwtService;
import com.app.nihongo.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/account")
@Tag(name = "Account Management", description = "APIs for user authentication and account management")
public class AccountController {
        @Autowired
        private IAccountService accountService;
        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private UserService userService;
        @Autowired
        private UserRepository userRepository;

        @Autowired
        private JwtService jwtService;

        @Operation(summary = "User Registration", description = "Register a new user account. An activation email will be sent to the provided email address.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Registration successful"),
                        @ApiResponse(responseCode = "400", description = "Invalid input or user already exists")
        })
        @PostMapping("/signup")
        public ResponseEntity<?> dangKyNguoiDung(
                        @Parameter(description = "User registration details", required = true) @RequestBody User user) {
                ResponseEntity<?> response = accountService.dangKyNguoiDung(user);
                return response;
        }

        @Operation(summary = "Activate Account", description = "Activate user account using email and activation code")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Account activated successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid activation code or email")
        })
        @GetMapping("/active")
        public ResponseEntity<?> dangKyNguoiDung(
                        @Parameter(description = "User email address", required = true) @RequestParam String email,
                        @Parameter(description = "Activation code sent to email", required = true) @RequestParam String activeNumber) {
                ResponseEntity<?> response = accountService.kichHoatTaiKHoan(email, activeNumber);
                return response;
        }

        @Operation(summary = "User Logout", description = "Clear authentication cookies and logout user")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Logout successful")
        })
        @PostMapping("/logout")
        public ResponseEntity<?> logout() {
                ResponseCookie clearToken = ResponseCookie.from("token", "")
                                .httpOnly(true)
                                .secure(true)
                                .sameSite("None")
                                .path("/")
                                .maxAge(0)
                                .build();
                ResponseCookie clearUid = ResponseCookie.from("uid", "")
                                .httpOnly(false)
                                .secure(true)
                                .sameSite("None")
                                .path("/")
                                .maxAge(0)
                                .build();
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.SET_COOKIE, clearToken.toString());
                headers.add(HttpHeaders.SET_COOKIE, clearUid.toString());
                return ResponseEntity.ok().headers(headers).body("Logged out");
        }

        @Operation(summary = "User Login", description = "Authenticate user and return JWT token in cookie")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(schema = @Schema(implementation = JwtResponse.class))),
                        @ApiResponse(responseCode = "400", description = "Invalid credentials or account not activated")
        })
        @PostMapping("/login")
        public ResponseEntity<?> dangNhap(
                        @Parameter(description = "Login credentials", required = true) @RequestBody LoginRequest loginRequest) {

                User user = userRepository.findByUsername(loginRequest.getUsername());
                if (user == null) {
                        return ResponseEntity.badRequest().body("User not found.");
                }
                if (!user.isActived()) {
                        return ResponseEntity.badRequest().body("Tài khoản chưa được kích hoạt.");
                }
                try {
                        Authentication authentication = authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                                        loginRequest.getPassword()));

                        if (authentication.isAuthenticated()) {
                                final String jwt = jwtService.generateToken(loginRequest.getUsername());
                                ResponseCookie tokenCookie = ResponseCookie.from("token", jwt)
                                                .httpOnly(true)
                                                .secure(true) // Required for SameSite=None
                                                .sameSite("None") // Required for cross-site cookies
                                                .path("/")
                                                .maxAge(24 * 60 * 60)
                                                .build();
                                ResponseCookie userIdCookie = ResponseCookie
                                                .from("uid", String.valueOf(user.getUserId()))
                                                .httpOnly(false)
                                                .secure(true) // Required for SameSite=None
                                                .sameSite("None") // Required for cross-site cookies
                                                .path("/")
                                                .maxAge(24 * 60 * 60)
                                                .build();
                                HttpHeaders headers = new HttpHeaders();
                                headers.add(HttpHeaders.SET_COOKIE, tokenCookie.toString());
                                headers.add(HttpHeaders.SET_COOKIE, userIdCookie.toString());
                                return ResponseEntity.ok()
                                                .headers(headers)
                                                .body(new JwtResponse(jwt));
                        }
                } catch (AuthenticationException e) {
                        return ResponseEntity.badRequest().body("Tên đăng nhập hoặc mật khẩu không chính xác.");
                }

                return ResponseEntity.badRequest().body("Xác thực không thành công.");
        }
}
