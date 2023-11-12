package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.DuAn;
import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.payload.DuAnResponse1;
import be.bds.bdsbes.service.dto.response.DuAnResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface DuAnRepository extends JpaRepository<DuAn, Long> {
    @Query(value = "select d.id, d.ma, d.ten, d.tien_do, d.chi_phi, d.ngay_bat_dau, d.ngay_ket_thuc, d.ghi_chu, d.trang_thai from", nativeQuery = true)
    List<DuAnResponse> getAllDuAn();
    @Query("select new be.bds.bdsbes.payload.DuAnResponse1(d.id, d.ma, d.ten, d.tienDo, d.chiPhi, d.ngayBatDau, d.ngayKetThuc, d.ghiChu, d.trangThai) from DuAn d  where d.id = ?1")
    DuAnResponse1 getDuAn(Long id);
    @Query("select d from DuAn d where d.ma like CONCAT('%', ?1, '%') or d.ten like CONCAT('%',?1, '%') order by d.ma desc")
    Page<DuAn> searchProject(Pageable pageable, String searchInput);
    @Transactional
    @Modifying
    @Query("UPDATE DuAn d SET d.trangThai = :trangThai WHERE d.id = :id")
    Integer updateTrangThaiById(int trangThai, Long id);
}