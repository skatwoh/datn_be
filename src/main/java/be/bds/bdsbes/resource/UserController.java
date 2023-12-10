package be.bds.bdsbes.resource;

import be.bds.bdsbes.security.UserPrincipal;
import be.bds.bdsbes.utils.AppConstantsUtil;
import be.bds.bdsbes.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.security.CurrentUser;
import be.bds.bdsbes.service.iService.IUserService;


@Slf4j
@RestController
@RequestMapping("/rpc/bds/user")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @GetMapping("/me")
    public ResponseEntity<?> getMe(@CurrentUser UserPrincipal userPrincipal) {
        try {
            return ResponseUtil.wrap(this.iUserService.getCurrentUser(userPrincipal));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        }
    }

    @PutMapping("/{email}/manual-active")
    public ResponseEntity<?> manualActive(@PathVariable String email) {
        try {
            return ResponseUtil.wrap(this.iUserService.manualActiveUser(email));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getList(
            @RequestParam(value = "page", defaultValue = AppConstantsUtil.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstantsUtil.DEFAULT_PAGE_SIZE) int size) {
        try {
            return ResponseUtil.wrap(this.iUserService.getUsers(page, size));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/detail/{email}")
    public ResponseEntity<?> getDetail(@PathVariable String email) {
        try {
            return ResponseUtil.wrap(this.iUserService.getUsersDetail(email));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/{email}/set-permission-user")
    public ResponseEntity<?> setUser(@PathVariable String email) {
        try {
            return ResponseUtil.wrap(this.iUserService.setPermissionUser(email));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        }
    }

    @PutMapping("/{email}/set-permission-admin")
    public ResponseEntity<?> setAdmin(@PathVariable String email) {
        try {
            return ResponseUtil.wrap(this.iUserService.setPermissionAdmin(email));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        }
    }

    @PutMapping("/{email}/set-permission-guest")
    public ResponseEntity<?> setGuest(@PathVariable String email) {
        try {
            return ResponseUtil.wrap(this.iUserService.setPermissionGuest(email));
        } catch (Exception ex) {
            log.error(this.getClass().getName(), ex);
            return ResponseUtil.generateErrorResponse(ex);
        }
    }
}