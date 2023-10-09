package be.bds.bdsbes.service.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface VoucherResponse {

    Long getId();

    String getMa();

    String getMo_Ta();

    BigDecimal getGiam_gia();

    LocalDate getNgay_Bat_Dau();

    LocalDate getNgay_Ket_Thuc();

    Integer getTrang_Thai();

    Integer getSo_Luong();




}
