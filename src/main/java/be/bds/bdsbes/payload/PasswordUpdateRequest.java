package be.bds.bdsbes.payload;

import javax.validation.constraints.NotBlank;

public class PasswordUpdateRequest {

    private String password;

    @NotBlank
    private String newPassword;


    public String getPassword() {
        return password;
    }

    public void setPassWord(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setEmail(String newPassword) {
        this.newPassword = newPassword;
    }
}
