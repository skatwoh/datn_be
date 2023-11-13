package be.bds.bdsbes.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PasswordUpdateRequest {

    private String password;

    @NotBlank
    private String newPassword;

    private String mess;
    private String email;
}
