package be.bds.bdsbes.service.dto.response;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface BaoTriResponse {
    Long getId();
    LocalDate getNgay_Bat_Dau();

    LocalDate getNgay_Ket_Thuc();

    BigDecimal getChi_Phi_Bao_Tri();
    String getGhi_Chu();

    Integer getTrang_Thai();
}
