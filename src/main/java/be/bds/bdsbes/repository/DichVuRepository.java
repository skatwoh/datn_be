package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.DichVu;
import be.bds.bdsbes.entities.DuAn;
import be.bds.bdsbes.payload.DichVuResponse1;
import be.bds.bdsbes.payload.DuAnResponse1;
import be.bds.bdsbes.service.dto.response.DichVuResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DichVuRepository extends JpaRepository<DichVu, Long> {
    @Query(value = "select dv.id, dv.ma, dv.ten_dich_vu, dv.ghi_chu, dv.gia_dich_vu, ts.trang_thai from dich_vu dv" , nativeQuery = true)
    List<DichVuResponse> getAllDv();
    @Query("select new be.bds.bdsbes.payload.DichVuResponse1(dv.id, dv.ma, dv.tenDichVu, dv.ghiChu, dv.giaDichVu, dv.trangThai) from DichVu dv  where dv.id = ?1")
    DichVuResponse1 getDichVu(Long id);
    @Query("select dv from DichVu dv where dv.ma like CONCAT('%', ?1, '%') or dv.tenDichVu like CONCAT('%',?1, '%') order by dv.ma desc")
    Page<DichVu> searchRoomService(Pageable pageable, String searchInput);
    @Transactional
    @Modifying
    @Query("UPDATE DichVu dv SET dv.trangThai = :trangThai WHERE dv.id = :id")
    Integer updateTrangThaiById(int trangThai, Long id);
}