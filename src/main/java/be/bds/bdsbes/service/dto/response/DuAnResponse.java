package be.bds.bdsbes.service.dto.response;

import org.hibernate.annotations.Nationalized;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface DuAnResponse {
    Long getId();
    String getMa();
    String getTen();
    Double getTien_Do();
    BigDecimal getChi_Phi();
    LocalDate getNgay_Bat_Dau();

    LocalDate getNgay_Ket_Thuc();
     String getGhi_Chu();
     Integer getTrang_Thai();
}
