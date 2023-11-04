package be.bds.bdsbes.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuanLyDoiTacResponse1 {
    Long id;
    private String ma;
    private String tenCongTy;
    private String ghiChu;
    private Integer trangThai;
}
