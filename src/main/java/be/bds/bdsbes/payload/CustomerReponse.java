package be.bds.bdsbes.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReponse {
    private long id;

    private String name;

    private String email;

    private String sdt;

    private LocalDate ngaySinh;

    private Boolean gioiTinh;

    private String diaChi;

    private String ghiChu;

    private String cccd;
}