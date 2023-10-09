package be.bds.bdsbes.service.dto.response;

import java.math.BigDecimal;

public interface DichVuResponse {
    Long getId();

    String getMa();

    String getTen_Dich_Vu();

    String getGhi_Chu();

    BigDecimal getGia_Dich_Vu();

    Integer getTrang_Thai();
}
