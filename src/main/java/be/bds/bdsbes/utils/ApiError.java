package be.bds.bdsbes.utils;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class ApiError {
    private String code;
    private String message;

    public ApiError(String code, String message) {
        this.code = code;
        this.message = MessageUtil.getMessage(message);
    }

    public ApiError(StatusError statusError, String deleteRoomOrder) {
    }
}
