package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;
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
import java.time.LocalDateTime;
import java.util.List;

@Repository("datPhongRepository")
public interface DatPhongRepository extends JpaRepository<DatPhong, Long> {

    @Query("select new be.bds.bdsbes.service.dto.response.DatPhongResponse(d.id, d.ma, u.id, u.ma, u.sdt, u.hoTen" +
            ",d.ngayDat, d.checkIn, d.checkOut, d.soNguoi, d.ghiChu, d.trangThai, d.phong.ma, d.tongGia, d.phong.id, d.phong.giaPhong, d.hoaDon.id) " +
            "from DatPhong d join d.khachHang u")
    List<DatPhongResponse> getAllDatPhong();

    @Query("select new be.bds.bdsbes.service.dto.response.DatPhongResponse(d.id, d.ma, u.id, u.ma, u.sdt, u.hoTen," +
            "d.ngayDat, d.checkIn, d.checkOut, d.soNguoi, d.ghiChu, d.trangThai, d.phong.ma, d.tongGia, d.phong.id, d.phong.giaPhong, d.hoaDon.id) " +
            "from DatPhong d join d.khachHang u where d.id= :id")
    DatPhongResponse get(Long id);

    @Query("select d from DatPhong d where d.khachHang.id = :id and d.trangThai = :trangThai")
    Page<DatPhong> getAllDatPhongByUser(Pageable pageable, Long id, Integer trangThai);

    @Query("SELECT CASE WHEN COUNT(dp) > 0 THEN true ELSE false END " +
            "FROM DatPhong dp " +
            "WHERE dp.phong.id = :idPhong and (dp.trangThai = 1 or dp.trangThai = 2 or dp.trangThai = 4) and (cast(dp.checkIn as date) = cast(:checkIn as date) " +
            "or (cast(dp.checkIn as date) < cast(:checkIn as date) and cast(dp.checkOut as date) > cast(:checkIn as date))" +
            "or (cast(dp.checkIn as date) >=" +
            " cast(:checkIn as date) and cast(dp.checkOut as date) <= cast(:checkOut as date)) " +
            "or (cast(dp.checkIn as date) > cast(:checkIn as date) and cast(dp.checkOut as date) > cast(:checkOut as date) and cast(dp.checkIn as date) < cast(:checkOut as date))  " +
            ")")
    Boolean validateCheckIn(@Param("idPhong") Long idPhong, @Param("checkIn") LocalDateTime checkIn, LocalDateTime checkOut);

    @Query("Select p from Phong  p inner join ChiTietPhong ctp on p.id = ctp.phong.id where p.trangThai = 1 and ctp.trangThai = 1 and  p.giaPhong = :giaPhong and p.id <> :id and p.id not in " +
            "(select d.phong.id from DatPhong d where (d.trangThai = 1 or d.trangThai = 2) and ((cast(:checkIn as date) between cast(d.checkIn as date) and cast(d.checkOut as date)) or (cast(:checkOut as date) between cast(d.checkIn as date) and cast(d.checkOut as date)) " +
            "or (cast(d.checkIn as date) between cast(:checkIn as date) and cast(:checkOut as date)) or (cast(d.checkOut as date) between cast(:checkIn as date) and cast(:checkOut as date)) or cast(:checkIn as date) = cast(d.checkIn as date) or" +
            " cast(:checkOut as date) = cast(d.checkOut as date)))")
    Page<Phong> getPhongByUpperPrice(Pageable pageable, BigDecimal giaPhong, Long id, LocalDateTime checkIn, LocalDateTime checkOut);


    @Transactional
    @Modifying
    @Query("UPDATE DatPhong d SET d.trangThai = :trangThai WHERE d.id = :id")
    Integer updateTrangThaiById(int trangThai, Long id);

    @Query("select new be.bds.bdsbes.service.dto.response.DatPhongResponse(d.id, d.ma, u.id, u.ma, u.sdt, u.hoTen," +
            "d.ngayDat, d.checkIn, d.checkOut, d.soNguoi, d.ghiChu, d.trangThai, d.phong.ma, d.tongGia, d.phong.id, d.phong.giaPhong, d.hoaDon.id) " +
            "from DatPhong d join d.khachHang u where d.id= :id")
    List<DatPhongResponse> getDatPhong(Long id);

    @Query("select p from DatPhong p where p.hoaDon.id = :id and (p.trangThai = 1 or p.trangThai = 2 or p.trangThai = 3)")
    List<DatPhong> getDatPhongByHoaDon(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE DatPhong d SET d.phong.id = :idPhong, d.tongGia = :tongGia WHERE d.id = :id")
    Integer updateDatPhongById(Long idPhong, BigDecimal tongGia, Long id);

    @Query("select d from DatPhong d where d.khachHang.id = :id ")
    Page<DatPhong> getLichSuDatPhong(Pageable pageable, Long id);

    @Query("select d from DatPhong d join HoaDon h on d.hoaDon.id = h.id where h.trangThai = 1 and h.khachHang.id = :idKH and d.trangThai = 4")
    Page<DatPhong> getRoomByHoaDon(Pageable pageable, Long idKH);

    @Query("select p from DatPhong p where p.hoaDon.id = :id")
    Page<DatPhong> getPageDatPhongByHoaDon(Pageable pageable, Long id);

    @Query("select d from DatPhong d join HoaDon h on d.hoaDon.id = h.id where d.hoaDon.id = :id")
    List<DatPhong> getRoomByHoaDon0(Long id);

    @Query("select count (dp.phong.id) from DatPhong dp where dp.trangThai = 1 and (cast(dp.ngayDat as date) between cast(:checkIn as date) and cast(:checkOut as date) )")
    int getSoPhongDaDat(LocalDate checkIn, LocalDate checkOut);

    @Query("select d from DatPhong d where d.khachHang.id = :id")
    Page<DatPhong> getPageDatPhongByKH(Pageable pageable, Long id);
}