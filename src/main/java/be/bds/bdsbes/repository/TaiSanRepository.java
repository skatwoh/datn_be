package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.TaiSan;
import be.bds.bdsbes.payload.TaiSanResponse1;
import be.bds.bdsbes.service.dto.response.TaiSanResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaiSanRepository extends JpaRepository<TaiSan, Long> {
    @Query(value = "select ts.id, ts.ma, ts.ten, ts.ghi_chu, ts.trang_thai from tai_san ts" , nativeQuery = true)
    List<TaiSanResponse> getAllTaiSan();
    @Query("select new be.bds.bdsbes.payload.TaiSanResponse1(ts.id, ts.ma, ts.ten, ts.ghiChu, ts.trangThai) from TaiSan ts  where ts.id = ?1")
    TaiSanResponse1 getTaiSan(Long id);
}