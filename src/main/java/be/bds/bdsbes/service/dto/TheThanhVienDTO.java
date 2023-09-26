package be.bds.bdsbes.service.dto;

import be.bds.bdsbes.entities.TheThanhVien;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class TheThanhVienDTO {

    @NotNull(message = "Not allow null")
    @NotBlank(message = "Not blank")
    @Length(max = 20, message = "Length must not exceed 20 characters")
    private String ma;

    @NotNull(message = "Not allow null")
    @NotBlank(message = "Not blank")
    @Length(max = 50, message = "Length must not exceed 50 characters")
    private String capBac;

    @NotNull(message = "Not allow null")
    private String ngayHetHan;

    @NotNull(message = "Not allow null")
    private BigDecimal giamGia;

    @NotNull(message = "Not allow null")
    @NotBlank(message = "Not blank")
    @Length(max = 100, message = "Length must not exceed 100 characters")
    private String ghiChu;

    public TheThanhVien dto(TheThanhVien theThanhVien) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        theThanhVien.setMa(this.getMa());
        theThanhVien.setCapBac(this.getCapBac());
        theThanhVien.setNgayHetHan(LocalDate.parse(this.getNgayHetHan(), formatter));
        theThanhVien.setGiamGia(this.getGiamGia());
        theThanhVien.setGhiChu(this.getGhiChu());
        return theThanhVien;
    }
}
