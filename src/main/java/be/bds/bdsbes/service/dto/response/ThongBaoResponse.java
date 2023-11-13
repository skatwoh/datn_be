package be.bds.bdsbes.service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ThongBaoResponse {
    private Long id;
    private Long userId;
    private String maDatPhong;
    private String noiDung;
    private LocalDateTime timestamp;
    private Integer trangThai;
}