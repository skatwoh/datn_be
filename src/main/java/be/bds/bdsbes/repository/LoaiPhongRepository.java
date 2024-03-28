package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.LoaiPhong;
import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.payload.LoaiPhongResponse1;
import be.bds.bdsbes.service.dto.response.LoaiPhongResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LoaiPhongRepository extends JpaRepository<LoaiPhong, Long> {

    @Query(value = "select new be.bds.bdsbes.payload.LoaiPhongResponse1(l.id, l.maLoaiPhong, l.tenLoaiPhong, l.soNguoi, l.tienIch, l.ghiChu, l.giaTheoNgay, l.giaTheoGio) from LoaiPhong l")
    List<LoaiPhongResponse1> singleListRoomType();

    @Query("select new be.bds.bdsbes.payload.LoaiPhongResponse1(l.id, l.maLoaiPhong, l.tenLoaiPhong, l.soNguoi, l.ghiChu, l.tienIch, l.giaTheoNgay, l.giaTheoGio) from LoaiPhong l where l.id = :id")
    LoaiPhongResponse1 get(Long id);

//    @Transactional
//    @Modifying
//    @Query("UPDATE LoaiPhong p SET p.trangThai = :trangThai WHERE p.id = :id")
//    Integer updateTrangThaiById(int trangThai, Long id);

    @Query("select count(p.id) from LoaiPhong l inner join Phong p on l.id = p.loaiPhong.id where count (p.id) >= :soPhong")
    int getSoPhongByLoaiPhong(int soPhong);

    @Query(value = "select new be.bds.bdsbes.payload.LoaiPhongResponse1(l.id, l.maLoaiPhong, l.tenLoaiPhong, l.soNguoi, l.tienIch, l.ghiChu, l.giaTheoNgay, l.giaTheoGio) from LoaiPhong l inner join Phong p on l.id = p.loaiPhong.id group by l.id, l.maLoaiPhong, l.tenLoaiPhong, l.soNguoi, l.tienIch, l.ghiChu, l.giaTheoNgay, l.giaTheoGio" +
            " having cast(count(p.id) as int) >= :soPhong" +
            " and (l.soNguoi = :soNguoi or l.soNguoi = (:soNguoi + 1) or l.soNguoi = (:soNguoi - 1))")
    List<LoaiPhongResponse1> listLoaiPhongBySearch1(int soPhong, int soNguoi);

    @Query(value = "select new be.bds.bdsbes.payload.LoaiPhongResponse1(l.id, l.maLoaiPhong, l.tenLoaiPhong, l.soNguoi, l.tienIch, l.ghiChu, l.giaTheoNgay, l.giaTheoGio) from LoaiPhong l inner join Phong p on l.id = p.loaiPhong.id group by l.id, l.maLoaiPhong, l.tenLoaiPhong, l.soNguoi, l.tienIch, l.ghiChu, l.giaTheoNgay, l.giaTheoGio" +
            " having cast(count(p.id) as int) >= :soPhong" +
            " and (l.soNguoi = :soNguoi or l.soNguoi = (:soNguoi + 1))")
    List<LoaiPhongResponse1> listLoaiPhongBySearch2(int soPhong, int soNguoi);

    @Query("select count(p.id) from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id inner join LoaiPhong l on p.loaiPhong.id = l.id where p.trangThai = 1 and ct.trangThai = 1 and p.id not in (select d.phong.id from DatPhong d where (d.trangThai = 1 or d.trangThai = 2) and ((cast(:checkIn as date) > cast(d.checkIn as date) and" +
            " cast(:checkIn as date) < cast(d.checkOut as date)) or (cast(:checkOut as date) > cast(d.checkIn as date) and cast(:checkOut as date) < cast(d.checkOut as date)) " +
            " or (cast(d.checkIn as date) > cast(:checkIn as date) and cast(d.checkIn as date) < cast(:checkOut as date)) or (cast(d.checkOut as date) > cast(:checkIn as date) and cast(d.checkOut as date) < cast(:checkOut as date)) or cast(:checkIn as date) = cast(d.checkIn as date) " +
            " or cast(:checkOut as date) = cast(d.checkOut as date))) and l.id = :idLoaiPhong")
    int getCountRoomByCheckDate(LocalDateTime checkIn, LocalDateTime checkOut, Long idLoaiPhong);
}