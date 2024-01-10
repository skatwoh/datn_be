package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.CustomerReponse;
import be.bds.bdsbes.payload.PermissionResponse;
import be.bds.bdsbes.payload.UserProfileResponse;
import be.bds.bdsbes.security.UserPrincipal;
import be.bds.bdsbes.payload.ManualActiveUserResponse;
import be.bds.bdsbes.utils.dto.PagedResponse;

public interface IUserService {
    /**
     * Get user profile
     * @param userPrincipal with model {@link UserPrincipal}
     * @return user info with model {@link UserProfileResponse}
     */
    public UserProfileResponse getCurrentUser(UserPrincipal userPrincipal);

    public ManualActiveUserResponse manualActiveUser(String email);

    public PagedResponse<UserProfileResponse> getUsers(int page, int size) throws ServiceException;
    public CustomerReponse getUsersDetail(String email) throws ServiceException;

    public PermissionResponse setPermissionUser(String email);
    public PermissionResponse setPermissionAdmin(String email);
    public PermissionResponse setPermissionGuest(String email);

}
