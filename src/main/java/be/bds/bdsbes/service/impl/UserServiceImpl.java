package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.domain.User;
import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.exception.ResourceNotFoundException;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.CustomerReponse;
import be.bds.bdsbes.payload.ManualActiveUserResponse;
import be.bds.bdsbes.payload.PermissionResponse;
import be.bds.bdsbes.payload.UserProfileResponse;
import be.bds.bdsbes.repository.KhachHangRepository;
import be.bds.bdsbes.repository.UserRepository;
import be.bds.bdsbes.security.UserPrincipal;
import be.bds.bdsbes.service.iService.IUserService;
import be.bds.bdsbes.service.mapper.UserMapper;
import be.bds.bdsbes.utils.AppConstantsUtil;
import be.bds.bdsbes.utils.ServiceExceptionBuilderUtil;
import be.bds.bdsbes.utils.ValidationErrorUtil;
import be.bds.bdsbes.utils.dto.KeyValue;
import be.bds.bdsbes.utils.dto.PagedResponse;
import be.bds.bdsbes.utils.dto.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("userServiceImpl")
public class UserServiceImpl implements IUserService {
    @Autowired
    private KhachHangRepository khachHangRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserProfileResponse getCurrentUser(UserPrincipal userPrincipal) {
        User user = this.userRepository.findById(userPrincipal.getId()).orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
        if (null != user) {
            return new UserProfileResponse(user.getId(), user.getName(), user.getEmail(), user.getImageUrl(), user.getEmailVerified(), user.getCreatedAt(), user.getUpdatedAt(), user.getRole(), user.getSdt());
        }
        return null;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ManualActiveUserResponse manualActiveUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            user.get().setEmailVerified(!user.get().getEmailVerified());
            userRepository.save(user.get());

            return new ManualActiveUserResponse(user.get().getId(), user.get().getEmailVerified());
        }
        return null;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PagedResponse<UserProfileResponse> getUsers(int page, int size) throws ServiceException {
        if (page <= 0) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("page", ValidationErrorUtil.Invalid))
                    .build();
        }

        if (size > AppConstantsUtil.MAX_PAGE_SIZE) {
            List<KeyValue> params = new ArrayList<>();
            params.add(new KeyValue("max", String.valueOf(AppConstantsUtil.MAX_PAGE_SIZE)));

            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("pageSize", ValidationErrorUtil.Invalid, params))
                    .build();
        }

        // Retrieve all entities
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.DESC, "createdAt");
        Page<User> entities = userRepository.findAll(pageable);

        return new PagedResponse<>(
                this.userMapper.toDtoList(entities.getContent()),
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }

    @Override
    public CustomerReponse getUsersDetail(String email) throws ServiceException {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Long idKH = user.getKhachHang().getId();
            Optional<KhachHang> khachHangOptional = khachHangRepository.findById(idKH);
            KhachHang khachHang = khachHangOptional.get();

            CustomerReponse customerReponse = new CustomerReponse();
            customerReponse.setId(user.getId());
            customerReponse.setName(user.getName());
            customerReponse.setEmail(user.getEmail());
            customerReponse.setSdt(user.getSdt());
            customerReponse.setDiaChi(khachHang.getDiaChi());
            customerReponse.setGioiTinh(khachHang.getGioiTinh());
            customerReponse.setNgaySinh(khachHang.getNgaySinh());
            customerReponse.setCccd(khachHang.getCccd());
            return customerReponse;
        } else {
            throw new ServiceException("Không tìm thấy người dùng với Email " + email);
        }
    }

    @Override
    public PermissionResponse setPermissionUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            user.get().setRole("user");
            userRepository.save(user.get());

            return new PermissionResponse(user.get().getId(), user.get().getRole());
        }
        return null;
    }

    @Override
    public PermissionResponse setPermissionAdmin(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            user.get().setRole("admin");
            userRepository.save(user.get());
            return new PermissionResponse(user.get().getId(), user.get().getRole());
        }
        return null;
    }

    @Override
    public PermissionResponse setPermissionGuest(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            user.get().setRole("");
            userRepository.save(user.get());

            return new PermissionResponse(user.get().getId(), user.get().getRole());
        }
        return null;
    }
}