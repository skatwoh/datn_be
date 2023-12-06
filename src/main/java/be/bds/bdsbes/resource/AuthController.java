package be.bds.bdsbes.resource;

import be.bds.bdsbes.domain.User;
import be.bds.bdsbes.payload.ForgotPasswordRequest;
import be.bds.bdsbes.payload.PasswordUpdateRequest;
import be.bds.bdsbes.service.impl.EmailService;
import be.bds.bdsbes.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.LoginRequest;
import be.bds.bdsbes.payload.SignUpRequest;
import be.bds.bdsbes.service.iService.IAuthService;


import javax.validation.Valid;
import java.util.Optional;
import java.util.Random;

@Slf4j
@RestController
@RequestMapping("/rpc/bds/auth")
public class AuthController {

    @Autowired
    private IAuthService iAuthService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()_+";
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        int passwordLength = 8;

        for (int i = 0; i < passwordLength; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            return ResponseUtil.wrap(this.iAuthService.authenticate(loginRequest));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        try {
            Boolean response = this.iAuthService.register(signUpRequest);
            return ResponseUtil.wrap(response);
        } catch (ServiceException e) {
            log.error(this.getClass().getName(), e);
            return ResponseUtil.generateErrorResponse(e);
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        }
    }

    @PostMapping("/forgot")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        try {
            // Kiểm tra xem người dùng có tồn tại trong hệ thống không
            Optional<User> optional = iAuthService.findUserByEmail(forgotPasswordRequest.getEmail());
            if (!optional.isPresent()) {
                // Người dùng không tồn tại, trả về lỗi
                return ResponseEntity.badRequest().body("Khong co email!");
            }

            // Tạo mật khẩu mới ngẫu nhiên
            String newPassword = generateRandomPassword();
            // Mã hóa mật khẩu mới bằng PasswordEncoder
            String encodedPassword = passwordEncoder.encode(newPassword);

            // Cập nhật mật khẩu mới cho người dùng
            User user = optional.get();
            iAuthService.updatePassword(user, encodedPassword);

            // Gửi email chứa mật khẩu mới cho người dùng
            emailService.sendPasswordResetEmail(user.getEmail(), newPassword);
            System.out.println(user.getEmail());
            System.out.println(newPassword);

            return ResponseUtil.wrap("Password reset email sent successfully");
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        }
    }

    @PutMapping("/update-password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        try {
            Optional<User> optional = iAuthService.findUserByEmail(passwordUpdateRequest.getEmail());

            if (optional.isPresent()) {
                User user = optional.get();
                if (passwordEncoder.matches(passwordUpdateRequest.getPassword(), user.getPassword())) {
                    String newPassword = passwordUpdateRequest.getNewPassword();

                    String encodedPassword = passwordEncoder.encode(newPassword);

                    iAuthService.updatePassword(user, encodedPassword);
                    System.out.println(newPassword);

                    return ResponseUtil.wrap("Update password successfully");
                } else {
                    String message = "Mật khẩu cũ sai!";
                    passwordUpdateRequest.setMess(message);
                    return ResponseUtil.wrap(passwordUpdateRequest);
                }
            } else {
                return ResponseUtil.wrap("User not found");
            }
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        }
    }


}