package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.HoaDon;
import be.bds.bdsbes.entities.Sale;
import be.bds.bdsbes.payload.HoaDonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {

    @Query("select new be.bds.bdsbes.payload.HoaDonResponse(p.id, p.ngayTao, p.ngayThanhToan, p.tongTien, p.trangThai, p.ghiChu, k.id, k.hoTen) from HoaDon p inner join p.khachHang k where p.trangThai <> 5 " +
            "group by k.id, p.id, p.ngayTao, p.ngayThanhToan, p.tongTien, p.trangThai, p.ghiChu, k.hoTen")
    Page<HoaDonResponse> getList(Pageable pageable);

    @Query("select new be.bds.bdsbes.payload.HoaDonResponse(p.id, p.ngayTao, p.ngayThanhToan, p.tongTien, p.trangThai, p.ghiChu, k.id, k.hoTen) from HoaDon p inner join p.khachHang k where p.trangThai <> 5 and (p.ghiChu like concat('%', :searchInput, '%') or " +
            "p.khachHang.hoTen like concat('%', :searchInput, '%'))" +
            "group by k.id, p.id, p.ngayTao, p.ngayThanhToan, p.tongTien, p.trangThai, p.ghiChu, k.hoTen")
    Page<HoaDonResponse> getListBySearch(Pageable pageable, String searchInput);

    @Query("select h from HoaDon h join DatPhong d on h.id = d.hoaDon.id join KhachHang k on k.id = h.khachHang.id where k.hoTen = :hoTen and k.sdt = :sdt and h.trangThai <> 5")
    Page<HoaDon> getListByCustumer(Pageable pageable, String hoTen, String sdt);

    @Query("select new be.bds.bdsbes.payload.HoaDonResponse(p.id, p.ngayTao, p.ngayThanhToan, p.tongTien, p.trangThai, p.ghiChu, k.id, k.hoTen) from HoaDon p inner join p.khachHang k " +
            "where k.id = :idKhachHang and cast(p.ngayTao as date) = cast(:ngayTao as date) and p.trangThai = 1")
    HoaDonResponse getHoaDon(Long idKhachHang, LocalDate ngayTao);

    @Query("select h.id from HoaDon h where h.khachHang.id = :idKH and cast(h.ngayTao as date) = cast(:ngayTao as date) and h.trangThai = 1")
    Long getId(Long idKH, LocalDate ngayTao);

    @Query("select new be.bds.bdsbes.payload.HoaDonResponse(p.id, p.ngayTao, p.ngayThanhToan, p.tongTien, p.trangThai, p.ghiChu, k.id, k.hoTen) from HoaDon p inner join p.khachHang k " +
            "where p.id = :id and p.trangThai <> 5")
    HoaDonResponse get(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE HoaDon h SET h.trangThai = :trangThai WHERE h.id = :id")
    Integer updateTrangThaiById(int trangThai, Long id);

    @Query(value = "SELECT * FROM hoa_don v WHERE v.trang_thai = :status", nativeQuery = true)
    List<HoaDon> findByStatus(@Param("status") Integer status);

    @Query(value = "SELECT * FROM hoa_don v WHERE v.ngay_tao < :date AND v.trang_thai = :status", nativeQuery = true)
    List<HoaDon> findByExpiryDateBeforeAndStatus(@Param("date") LocalDate date, @Param("status") Integer status);

    @Query("select new be.bds.bdsbes.payload.HoaDonResponse(p.id, p.ngayTao, p.ngayThanhToan, p.tongTien, p.trangThai, p.ghiChu, k.id, k.hoTen) from HoaDon p inner join p.khachHang k " +
            "where k.id = :idKhachHang and cast(p.ngayTao as date) = cast(:ngayTao as date) and p.trangThai = 1")
    HoaDonResponse getHoaDon0(Long idKhachHang, LocalDate ngayTao);

    @Query("select h.id from HoaDon h where h.khachHang.id = :idKH and cast(h.ngayTao as date) = cast(:ngayTao as date) and (h.trangThai = 1 or h.trangThai = 3)")
    Long getIdTaiQuay(Long idKH, LocalDate ngayTao);
    @Query("select new be.bds.bdsbes.payload.HoaDonResponse(p.id, p.ngayTao, p.ngayThanhToan, p.tongTien, p.trangThai, p.ghiChu, k.id, k.hoTen) from HoaDon p inner join p.khachHang k " +
            "where k.id = :idKhachHang and cast(p.ngayTao as date) = cast(:ngayTao as date) and (p.trangThai = 3 or p.trangThai = 1)")
    HoaDonResponse getHoaDonTaiQuay(Long idKhachHang, LocalDate ngayTao);

    @Query("select sum(h.tongTien) from HoaDon h where h.trangThai = 0 and (h.ngayThanhToan between cast(:checkIn as date) and cast(:checkOut as date) )")
    BigDecimal getDoanhThuByDay(LocalDate checkIn, LocalDate checkOut);

    @Query("select sum(h.tongTien) from HoaDon h where h.trangThai = 0 and (month(h.ngayThanhToan) = :month and year(h.ngayThanhToan) = :year)")
    BigDecimal getDoanhThuByMonth(int month, int year);

    @Query("select sum(h.tongTien) from HoaDon h where h.trangThai = 0 and (year(h.ngayThanhToan) = :year)")
    BigDecimal getDoanhThuByYear(int year);

    @Query("select sum(h.tongTien) from HoaDon h where h.trangThai = 0 and (day(h.ngayThanhToan) = :day and month(h.ngayThanhToan) = :month and year(h.ngayThanhToan) = :year)")
    BigDecimal getDoanhThuByToDay(int day, int month, int year);

    @Query("select sum(h.tongTien) from HoaDon h where h.trangThai = 0")
    BigDecimal getAllDoanhThu();
}