package be.bds.bdsbes.resource;

import be.bds.bdsbes.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.LoginRequest;
import be.bds.bdsbes.payload.SignUpRequest;
import be.bds.bdsbes.service.IAuthService;


import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/rpc/bds/auth")
public class AuthController {

    @Autowired
    private IAuthService iAuthService;

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

}