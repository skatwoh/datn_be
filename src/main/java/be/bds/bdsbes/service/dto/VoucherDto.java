package be.bds.bdsbes.service.dto;

import be.bds.bdsbes.entities.Voucher;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * DTO for {@link be.bds.bdsbes.entities.Voucher}
 */
@Value
public class VoucherDto implements Serializable {
    @NotNull
    @Size(max = 20)
    String ma;
    String moTa;
    @NotNull(message = "Không thể để trống trường này")
    BigDecimal giamGia;
    @NotNull(message = "Không thể để trống trường này")
    LocalDate ngayBatDau;
    @NotNull(message = "Không thể để trống trường này")
    LocalDate ngayKetThuc;
    @NotNull(message = "Không thể để trống trường này")
    Integer trangThai;
    @NotNull(message = "Không thể để trống trường này")
    Integer soLuong;


    public Voucher dtoVoucher(Voucher voucher){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        voucher.setMa(this.getMa());
        voucher.setMoTa(this.getMoTa());
        voucher.setGiamGia(this.getGiamGia());
        voucher.setNgayBatDau(this.getNgayBatDau());
        voucher.setNgayKetThuc(this.getNgayKetThuc());
        voucher.setTrangThai(this.getTrangThai());
        voucher.setSoLuong(this.getSoLuong());
        return voucher;
    }
}