package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.HoaDon;
import be.bds.bdsbes.payload.HoaDonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {

    @Query("select new be.bds.bdsbes.payload.HoaDonResponse(p.id, p.ngayTao, p.ngayThanhToan, p.tongTien, p.trangThai, p.ghiChu, k.id, k.hoTen) from HoaDon p inner join p.khachHang k " +
            "group by k.id, p.id, p.ngayTao, p.ngayThanhToan, p.tongTien, p.trangThai, p.ghiChu, k.hoTen")
    Page<HoaDonResponse> getList(Pageable pageable);

    @Query("select h from HoaDon h join DatPhong d on h.id = d.hoaDon.id join KhachHang k on k.id = h.khachHang.id where k.hoTen = :hoTen and k.sdt = :sdt")
    Page<HoaDon> getListByCustumer(Pageable pageable, String hoTen, String sdt);

    @Query("select new be.bds.bdsbes.payload.HoaDonResponse(p.id, p.ngayTao, p.ngayThanhToan, p.tongTien, p.trangThai, p.ghiChu, k.id, k.hoTen) from HoaDon p inner join p.khachHang k " +
            "where k.id = :idKhachHang and cast(p.ngayTao as date) = cast(:ngayTao as date)")
    HoaDonResponse getHoaDon(Long idKhachHang, LocalDate ngayTao);

    @Query("select h.id from HoaDon h where h.khachHang.id = :idKH and cast(h.ngayTao as date) = cast(:ngayTao as date)")
    Long getId(Long idKH, LocalDate ngayTao);
}