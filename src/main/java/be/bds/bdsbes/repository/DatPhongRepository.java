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

    @Query("select new be.bds.bdsbes.service.dto.response.DatPhongResponse(d.id, d.ma, k.id, k.ma, k.sdt, u.name, v.id, v.ma," +
            " v.giamGia, d.ngayDat, d.checkIn, d.checkOut, d.soNguoi, d.ghiChu, d.trangThai, d.phong.ma, d.tongGia, d.phong.id, d.phong.giaPhong, d.hoaDon.id) " +
            "from DatPhong d join d.user u join u.khachHang k left join d.voucher v")
    List<DatPhongResponse> getAllDatPhong();

    @Query("select new be.bds.bdsbes.service.dto.response.DatPhongResponse(d.id, d.ma, k.id, k.ma, k.sdt, u.name, v.id, v.ma," +
            " v.giamGia, d.ngayDat, d.checkIn, d.checkOut, d.soNguoi, d.ghiChu, d.trangThai, d.phong.ma, d.tongGia, d.phong.id, d.phong.giaPhong, d.hoaDon.id) " +
            "from DatPhong d join d.user u join u.khachHang k left join d.voucher v where d.id= :id")
    DatPhongResponse get(Long id);

    @Query("select d from DatPhong d where d.user.id = :id and d.trangThai = :trangThai")
    Page<DatPhong> getAllDatPhongByUser(Pageable pageable, Long id, Integer trangThai);

    @Query("SELECT CASE WHEN COUNT(dp) > 0 THEN true ELSE false END " +
            "FROM DatPhong dp " +
            "WHERE dp.phong.id = :idPhong and (dp.trangThai = 1 or dp.trangThai = 2) and (cast(dp.checkIn as date) = cast(:checkIn as date) " +
            "or (cast(dp.checkIn as date) < cast(:checkIn as date) and cast(dp.checkOut as date) > cast(:checkIn as date))" +
            "or (cast(dp.checkIn as date) >=" +
            " cast(:checkIn as date) and cast(dp.checkOut as date) <= cast(:checkOut as date)) )")
    Boolean validateCheckIn(@Param("idPhong") Long idPhong, @Param("checkIn") LocalDateTime checkIn, LocalDateTime checkOut);

    @Query("Select p from Phong  p inner join ChiTietPhong ctp on p.id = ctp.phong.id where p.trangThai = 1 and ctp.trangThai = 1 and  p.giaPhong >= :giaPhong and p.id <> :id")
    Page<Phong> getPhongByUpperPrice(Pageable pageable, BigDecimal giaPhong, Long id);


    @Transactional
    @Modifying
    @Query("UPDATE DatPhong d SET d.trangThai = :trangThai WHERE d.id = :id")
    Integer updateTrangThaiById(int trangThai, Long id);

    @Query("select new be.bds.bdsbes.service.dto.response.DatPhongResponse(d.id, d.ma, k.id, k.ma, k.sdt, u.name, v.id, v.ma," +
            " v.giamGia, d.ngayDat, d.checkIn, d.checkOut, d.soNguoi, d.ghiChu, d.trangThai, d.phong.ma, d.tongGia, d.phong.id, d.phong.giaPhong, d.hoaDon.id) " +
            "from DatPhong d join d.user u join u.khachHang k left join d.voucher v where d.id= :id")
    List<DatPhongResponse> getDatPhong(Long id);

    @Query("select p from DatPhong p where p.hoaDon.id = :id and p.trangThai = 1")
    List<DatPhong> getDatPhongByHoaDon(Long id);

    @Transactional
    @Modifying
    @Query("UPDATE DatPhong d SET d.phong.id = :idPhong, d.tongGia = :tongGia WHERE d.id = :id")
    Integer updateDatPhongById(Long idPhong, BigDecimal tongGia, Long id);

    @Query("select d from DatPhong d where d.user.id = :id ")
    Page<DatPhong> getLichSuDatPhong(Pageable pageable, Long id);

    @Query("select d from DatPhong d join HoaDon h on d.hoaDon.id = h.id where h.trangThai = 1 and h.khachHang.id = :idKH and d.trangThai = 1")
    Page<DatPhong> getRoomByHoaDon(Pageable pageable, Long idKH);

    @Query("select p from DatPhong p where p.hoaDon.id = :id")
    Page<DatPhong> getPageDatPhongByHoaDon(Pageable pageable, Long id);
}