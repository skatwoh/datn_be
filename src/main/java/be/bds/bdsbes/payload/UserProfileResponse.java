package be.bds.bdsbes.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponse {
    private long id;

    private String name;

    private String email;

    private String imageUrl;

    private Boolean emailVerified = false;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String role;

    private String sdt;
}