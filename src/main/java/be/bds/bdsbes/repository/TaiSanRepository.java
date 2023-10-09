package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.TaiSan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import be.bds.bdsbes.service.dto.response.TaiSanResponse;
import java.util.List;


@Repository
public interface TaiSanRepository extends JpaRepository<TaiSan, Long> {
    @Query(value = "select ts.id, ts.ma, ts.ten, ts.ghi_chu, ts.trang_thai from tai_san ts" , nativeQuery = true)
    List<TaiSanResponse> getAllTaiSan();
}