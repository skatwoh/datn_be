package be.bds.bdsbes.payload;

import be.bds.bdsbes.entities.ChiTietPhong;
import be.bds.bdsbes.entities.TaiSan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CoSoVatChatResponse1 {
    private Long id;

    private TaiSan idTaiSan;

    private Integer soLuong;

    private String ghiChu;

    private ChiTietPhong idChiTietPhong;

    private Integer trangThai;
}
