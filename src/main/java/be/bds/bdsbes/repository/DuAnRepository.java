package be.bds.bdsbes.repository;
import be.bds.bdsbes.entities.DuAn;
import be.bds.bdsbes.service.dto.response.DuAnResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface DuAnRepository extends JpaRepository<DuAn, Long> {
    @Query(value = "select d.id, d.ma, d.ten, d.tien_do, d.chi_phi, d.ngay_bat_dau, d.ngay_ket_thuc, d.ghi_chu, d.trang_thai from", nativeQuery = true)
    List<DuAnResponse> getAllDuAn();
}