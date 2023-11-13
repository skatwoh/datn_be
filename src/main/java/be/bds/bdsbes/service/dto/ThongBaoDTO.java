package be.bds.bdsbes.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;

/**
 * DTO for {@link be.bds.bdsbes.entities.ThongBao}
 */
@Getter
@Setter
public class ThongBaoDTO implements Serializable {
    Long userId;
    String maDatPhong;
    String noiDung;
    LocalDateTime timestamp;
    Integer trangThai;
}