package be.bds.bdsbes.service.dto.response;

import java.math.BigDecimal;

public interface PhongResponse {

    Long getId();

    String getMa();

    Long getId_Loai_Phong();

    String getMa_Loai_Phong();

    String getTen_Loai_Phong();

    BigDecimal getGia_Phong();

    Integer getTrang_Thai();
}
