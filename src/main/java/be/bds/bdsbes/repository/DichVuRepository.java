package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.DichVu;
import be.bds.bdsbes.service.dto.response.DichVuResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DichVuRepository extends JpaRepository<DichVu, Long> {
    @Query(value = "select dv.id, dv.ma, dv.ten_dich_vu, dv.ghi_chu, dv.gia_dich_vu, ts.trang_thai from dich_vu dv" , nativeQuery = true)
    List<DichVuResponse> getAllDv();
}