package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.domain.User;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.SignUpRequest;
import be.bds.bdsbes.repository.RoleRepository;
import be.bds.bdsbes.repository.UserRepository;
import be.bds.bdsbes.security.TokenProvider;
import be.bds.bdsbes.service.mapper.SignUpMapper;
import be.bds.bdsbes.utils.SequenceGeneratorUtil;
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
import be.bds.bdsbes.service.IAuthService;
import be.bds.bdsbes.utils.ServiceExceptionBuilderUtil;
import be.bds.bdsbes.utils.dto.ValidationErrorResponse;

import java.util.Collections;
import java.util.Optional;

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

        Optional<Role> userRole = roleRepository.findByName(RoleName.ROLE_GUEST);
        if (userRole.isPresent()) {
            user.setRoles(Collections.singleton(userRole.get()));
        } else {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("role", ValidationErrorUtil.NotFound))
                    .build();
        }

        user.setId(new SequenceGeneratorUtil().nextId());
        user.setProvider(AuthProvider.local);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

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
}