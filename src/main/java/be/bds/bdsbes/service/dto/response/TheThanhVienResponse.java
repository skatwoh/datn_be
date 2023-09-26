package be.bds.bdsbes.service.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface TheThanhVienResponse {

    Long getId();

    String getMa();

    String getCap_Bac();

    LocalDate getNgay_Het_Han();

    BigDecimal getGiam_Gia();

    String getGhi_Chu();
}
