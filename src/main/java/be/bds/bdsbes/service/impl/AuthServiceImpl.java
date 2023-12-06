package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.domain.User;
import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.entities.TheThanhVien;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.SignUpRequest;
import be.bds.bdsbes.repository.RoleRepository;
import be.bds.bdsbes.repository.UserRepository;
import be.bds.bdsbes.security.TokenProvider;
import be.bds.bdsbes.service.mapper.SignUpMapper;
import be.bds.bdsbes.utils.ValidationErrorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import be.bds.bdsbes.domain.AuthProvider;
import be.bds.bdsbes.domain.Role;
import be.bds.bdsbes.domain.RoleName;
import be.bds.bdsbes.payload.LoginRequest;
import be.bds.bdsbes.payload.LoginResponse;
import be.bds.bdsbes.service.iService.IAuthService;
import be.bds.bdsbes.utils.ServiceExceptionBuilderUtil;
import be.bds.bdsbes.utils.dto.ValidationErrorResponse;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service("authServiceImpl")
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private SignUpMapper signUpMapper;

    @Override
    public Boolean register(SignUpRequest signUpRequest) throws ServiceException {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("email", ValidationErrorUtil.Duplicate))
                    .build();
        }

        User user = this.signUpMapper.toEntity(signUpRequest);
        Random random = new Random();
        int randomId = random.nextInt(1000000);

        Optional<Role> userRole = roleRepository.findByName(RoleName.ROLE_GUEST);
        if (userRole.isPresent()) {
            user.setRoles(Collections.singleton(userRole.get()));
        } else {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("role", ValidationErrorUtil.NotFound))
                    .build();
        }

        int min = 1;
        int max = Integer.MAX_VALUE;
        int ma = random.nextInt(max - min + 1) + min;

        user.setId((long) randomId);
        user.setSdt(user.getSdt());
        user.setProvider(AuthProvider.local);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEmailVerified(true);
        user.setKhachHang(KhachHang.builder().id(user.getId()).ma("KH" + ma).hoTen(user.getName()).sdt(user.getSdt())
                .theThanhVien(TheThanhVien.builder().id(1L).build()).build());

        this.userRepository.save(user);
        return true;
    }

    @Override
    public LoginResponse authenticate(LoginRequest loginRequest) {
        Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
        if (user.isPresent() && user.get().getEmailVerified()) {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new LoginResponse(tokenProvider.createToken(authentication), "Bearer", null);
        }

        return null;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void updatePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }
}