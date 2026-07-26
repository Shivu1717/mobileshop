package com.searv.mobileshop.controller;

import com.searv.mobileshop.dto.LoginRequest;
import com.searv.mobileshop.dto.ResetPasswordRequest;
import com.searv.mobileshop.dto.SendOtpRequest;
import com.searv.mobileshop.dto.VerifyOtpRequest;
import com.searv.mobileshop.entity.AdminUser;
import com.searv.mobileshop.respository.AdminUserRepository;
import com.searv.mobileshop.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {
        "http://localhost:3000",
        "https://jagdamba-mobile-shoppe-electronics.netlify.app"
})
public class LoginController {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private JavaMailSender mailSender;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;


    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody LoginRequest request) {

        Map<String, Object> response = new HashMap<>();

        AdminUser user = adminUserRepository.findByUsername(request.getUsername());

        if (user == null) {
            response.put("status", "error");
            response.put("message", "Invalid username or password");
            return response;
        }

        boolean passwordMatch = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!passwordMatch) {
            response.put("status", "error");
            response.put("message", "Invalid username or password");
            return response;
        }

        String token = jwtUtil.generateToken(user.getUsername());

        response.put("status", "success");
        response.put("message", "Login successful");
        response.put("token", token);
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());

        return response;
    }

    @PostMapping("/forgot/send-otp")
    public Map<String, Object> sendOtp(@RequestBody SendOtpRequest request) {
        System.out.println("SEND OTP API HIT");
        Map<String, Object> response = new HashMap<>();

        AdminUser user = findUserByIdentifier(request.getIdentifier(), request.getOtpType());

        if (user == null) {
            response.put("success", false);
            response.put("message", "User not found");
            return response;
        }

        String otp = String.valueOf((int) (Math.random() * 900000) + 100000);

        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        user.setOtpVerified("N");
        adminUserRepository.save(user);

        try {
            if ("EMAIL".equalsIgnoreCase(request.getOtpType())) {
                sendOtpEmail(user.getEmail(), otp);
                response.put("message", "OTP sent to email successfully");
            } else if ("MOBILE".equalsIgnoreCase(request.getOtpType())) {
                System.out.println("Mobile OTP for " + user.getMobileNumber() + " is: " + otp);
                response.put("message", "OTP sent to mobile successfully");
            } else {
                response.put("success", false);
                response.put("message", "Invalid OTP type");
                return response;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.put("success", true);
        return response;
    }

    @PostMapping("/forgot/verify-otp")
    public Map<String, Object> verifyOtp(@RequestBody VerifyOtpRequest request) {
        Map<String, Object> response = new HashMap<>();

        AdminUser user = findUserByIdentifier(request.getIdentifier(), request.getOtpType());

        if (user == null) {
            response.put("success", false);
            response.put("message", "User not found");
            return response;
        }

        if (user.getOtp() == null || !user.getOtp().equals(request.getOtp())) {
            response.put("success", false);
            response.put("message", "Invalid OTP");
            return response;
        }

        if (user.getOtpExpiry() == null || user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            response.put("success", false);
            response.put("message", "OTP expired");
            return response;
        }

        user.setOtpVerified("Y");
        adminUserRepository.save(user);

        response.put("success", true);
        response.put("message", "OTP verified successfully");
        return response;
    }

    @PostMapping("/forgot/reset-password")
    public Map<String, Object> resetPassword(@RequestBody ResetPasswordRequest request) {
        Map<String, Object> response = new HashMap<>();

        AdminUser user = findUserByIdentifier(request.getIdentifier(), request.getOtpType());

        if (user == null) {
            response.put("success", false);
            response.put("message", "User not found");
            return response;
        }

        if (!"Y".equals(user.getOtpVerified())) {
            response.put("success", false);
            response.put("message", "Please verify OTP first");
            return response;
        }

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            response.put("success", false);
            response.put("message", "Password not matched");
            return response;
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setOtp(null);
        user.setOtpExpiry(null);
        user.setOtpVerified("N");

        adminUserRepository.save(user);

        response.put("success", true);
        response.put("message", "Password reset successfully");
        return response;
    }

    private AdminUser findUserByIdentifier(String identifier, String otpType) {
        if ("EMAIL".equalsIgnoreCase(otpType)) {
            return adminUserRepository.findByEmail(identifier);
        } else if ("MOBILE".equalsIgnoreCase(otpType)) {
            return adminUserRepository.findByMobileNumber(identifier);
        }
        return null;
    }

    private void sendOtpEmail(String toEmail, String otp) {

        try {

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("shivaratnaraut@gmail.com");

            message.setTo(toEmail);

            message.setSubject("Jagadamb Mobile Shop - Password Reset OTP");

            message.setText(
                    "Dear Customer,\n\n" +
                            "Your One Time Password (OTP) for password reset is : " + otp +
                            "\n\nThis OTP is valid for 5 minutes." +
                            "\n\nPlease do not share this OTP with anyone." +
                            "\n\nRegards,\nJagadamba Mobile Shop & Electronics"
            );

            mailSender.send(message);

        } catch (Exception e) {

            throw new RuntimeException("Failed to send OTP email : " + e.getMessage());

        }
    }
}