package be.bds.bdsbes.service;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.SignUpRequest;
import be.bds.bdsbes.payload.LoginRequest;
import be.bds.bdsbes.payload.LoginResponse;

public interface IAuthService {
    /**
     * Register user
     * @param signUpRequest payload with model {@link SignUpRequest}
     * @return return true or false
     */
    public Boolean register(SignUpRequest signUpRequest) throws ServiceException;

    /**
     * Login user
     * @param loginRequest payload with model {@link LoginRequest}
     * @return a token with model {@link LoginResponse}
     */
    public LoginResponse authenticate(LoginRequest loginRequest);
}
