package be.bds.bdsbes.service.dto.response;

import java.math.BigDecimal;

public interface ChiTietDichVuResponse {
    Long getId();
    String getId_Dich_Vu();
    String getId_Dat_Phong();
    String getGhi_Chu();
    BigDecimal getGia_Dich_Vu();
    Integer getTrang_Thai();

}
