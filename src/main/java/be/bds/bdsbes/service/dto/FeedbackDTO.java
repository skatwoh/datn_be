package be.bds.bdsbes.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class FeedbackDTO {

    private Long idChiTietPhong;
    private Long idKhachHang;
    private String moTa;
    private Integer trangThai;
}
