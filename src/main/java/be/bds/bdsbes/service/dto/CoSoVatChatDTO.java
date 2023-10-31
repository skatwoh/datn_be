package be.bds.bdsbes.service.dto;

import be.bds.bdsbes.entities.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CoSoVatChatDTO {
    private  Long idTaiSan;
    @NotNull(message = "Not allow null")
    private Integer soLuong;
    @NotNull(message = "Not allow null")
    @NotBlank(message = "Not blank")
    @Length(max = 100, message = "Length must not exceed 100 characters")
    private String ghiChu;
    private Long idChiTietPhong;
    @NotNull(message = "Not allow null")
    private Integer trangthai;

    public CoSoVatChat dto(CoSoVatChat coSoVatChat) {
        coSoVatChat.setTaiSan(TaiSan.builder().id(this.getIdTaiSan()).build());
        coSoVatChat.setSoLuong(this.getSoLuong());
        coSoVatChat.setGhiChu(this.getGhiChu());
        coSoVatChat.setChiTietPhong(ChiTietPhong.builder().id(this.getIdChiTietPhong()).build());
        coSoVatChat.setTrangThai(this.getTrangthai());
        return coSoVatChat;
    }
}
