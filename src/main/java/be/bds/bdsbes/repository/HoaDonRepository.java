package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.HoaDon;
import be.bds.bdsbes.payload.HoaDonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {

    @Query("select new be.bds.bdsbes.payload.HoaDonResponse(p.id, p.ngayTao, p.ngayThanhToan, p.tongTien, p.trangThai, p.ghiChu, k.id, k.hoTen) from HoaDon p inner join p.khachHang k " +
            "group by k.id, p.id, p.ngayTao, p.ngayThanhToan, p.tongTien, p.trangThai, p.ghiChu, k.hoTen")
    Page<HoaDonResponse> getList(Pageable pageable);

    @Query("select h from HoaDon h join DatPhong d on h.id = d.hoaDon.id join KhachHang k on k.id = h.khachHang.id where k.hoTen = :hoTen and k.sdt = :sdt")
    Page<HoaDon> getListByCustumer(Pageable pageable, String hoTen, String sdt);

    @Query("select new be.bds.bdsbes.payload.HoaDonResponse(p.id, p.ngayTao, p.ngayThanhToan, p.tongTien, p.trangThai, p.ghiChu, k.id, k.hoTen) from HoaDon p inner join p.khachHang k " +
            "where k.id = :idKhachHang and cast(p.ngayTao as date) = cast(:ngayTao as date) and p.trangThai = 1")
    HoaDonResponse getHoaDon(Long idKhachHang, LocalDate ngayTao);

    @Query("select h.id from HoaDon h where h.khachHang.id = :idKH and cast(h.ngayTao as date) = cast(:ngayTao as date) and h.trangThai = 1")
    Long getId(Long idKH, LocalDate ngayTao);

    @Query("select new be.bds.bdsbes.payload.HoaDonResponse(p.id, p.ngayTao, p.ngayThanhToan, p.tongTien, p.trangThai, p.ghiChu, k.id, k.hoTen) from HoaDon p inner join p.khachHang k " +
            "where p.id = :id")
    HoaDonResponse get(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE HoaDon h SET h.trangThai = :trangThai WHERE h.id = :id")
    Integer updateTrangThaiById(int trangThai, Long id);

    @Query(value = "SELECT * FROM hoa_don v WHERE v.trang_thai = :status", nativeQuery = true)
    List<HoaDon> findByStatus(@Param("status") Integer status);
}