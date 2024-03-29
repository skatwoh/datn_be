package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.payload.PhongResponse1;
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

@Repository
public interface PhongRepository extends JpaRepository<Phong, Long> {

    @Query("select new be.bds.bdsbes.payload.PhongResponse1(p.id, p.ma, p.giaPhong, p.trangThai, l.id, l.tenLoaiPhong, p.image) from Phong p inner join p.loaiPhong l where p.id = ?1")
    PhongResponse1 get(Long id);

    @Query(value = "select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id where p.trangThai = 1 and ct.trangThai = 1")
    Page<Phong> getListRoom(Pageable pageable);

    @Query("select new be.bds.bdsbes.payload.PhongResponse1(p.id, p.ma, p.giaPhong, p.trangThai, l.id, l.tenLoaiPhong, p.image) from Phong p inner join p.loaiPhong l where p.id not in (select ct.phong.id from ChiTietPhong ct)")
    List<PhongResponse1> singleListRoom();

    @Query("select p from Phong p where p.ma like CONCAT('%', ?1, '%') or p.loaiPhong.tenLoaiPhong like CONCAT('%',?1, '%')")
    Page<Phong> searchRoom(Pageable pageable, String searchInput);


    @Transactional
    @Modifying
    @Query("UPDATE Phong p SET p.trangThai = :trangThai WHERE p.id = :id")
    Integer updateTrangThaiById(int trangThai, Long id);

//    @Query("select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id inner join LoaiPhong l on l.id = p.loaiPhong.id " +
//            "where p.trangThai = 1 and ct.trangThai = 1 and ct.soLuongNguoi = :soLuongNguoi and l.tenLoaiPhong like :tenLoaiPhong and p.id not in (select d.phong.id from DatPhong d where ((cast(:checkIn as date) > cast(d.checkIn as date) and cast(:checkIn as date) < cast(d.checkOut as date)) or (cast(:checkOut as date) > cast(d.checkIn as date) and cast(:checkOut as date) < cast(d.checkOut as date))" +
//            "or (cast(d.checkIn as date) > cast(:checkIn as date) and cast(d.checkIn as date) < cast(:checkOut as date)) or (cast(d.checkOut as date) > cast(:checkIn as date) and cast(d.checkOut as date) < cast(:checkOut as date)) or cast(:checkIn as date) = cast(d.checkIn as date) " +
//            "or cast(:checkOut as date) = cast(d.checkOut as date)) and (d.trangThai = 1 or d.trangThai = 2 or d.trangThai = 4)) order by p.ma asc")
//    Page<Phong> searchRoomManager(Pageable pageable, Integer soLuongNguoi, String tenLoaiPhong, LocalDateTime checkIn, LocalDateTime checkOut);
//
//    @Query("select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id inner join LoaiPhong l on l.id = p.loaiPhong.id " +
//            "where p.trangThai = 1 and ct.trangThai = 1 and ct.soLuongNguoi >= :soLuongNguoi and l.tenLoaiPhong like :tenLoaiPhong and p.id not in (select d.phong.id from DatPhong d where ((cast(:checkIn as date) > cast(d.checkIn as date) and cast(:checkIn as date) < cast(d.checkOut as date)) or (cast(:checkOut as date) > cast(d.checkIn as date) and cast(:checkOut as date) < cast(d.checkOut as date))" +
//            "or (cast(d.checkIn as date) > cast(:checkIn as date) and cast(d.checkIn as date) < cast(:checkOut as date)) or (cast(d.checkOut as date) > cast(:checkIn as date) and cast(d.checkOut as date) < cast(:checkOut as date)) or cast(:checkIn as date) = cast(d.checkIn as date) " +
//            "or cast(:checkOut as date) = cast(d.checkOut as date)) and (d.trangThai = 1 or d.trangThai = 2 or d.trangThai = 4)) order by p.ma asc")
//    Page<Phong> searchRoomManager3(Pageable pageable, Integer soLuongNguoi, String tenLoaiPhong, LocalDateTime checkIn, LocalDateTime checkOut);
//
//    @Query("select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id inner join LoaiPhong l on l.id = p.loaiPhong.id " +
//            "where p.trangThai = 1 and ct.trangThai = 1 and ct.soLuongNguoi >= :soLuongNguoi and l.tenLoaiPhong like :tenLoaiPhong order by p.ma asc")
//    Page<Phong> searchRoomManager2(Pageable pageable, Integer soLuongNguoi, String tenLoaiPhong);
//
//    @Query("select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id inner join LoaiPhong l on l.id = p.loaiPhong.id " +
//            "where p.trangThai = 1 and ct.trangThai = 1 and ct.soLuongNguoi = :soLuongNguoi and l.tenLoaiPhong like :tenLoaiPhong order by p.ma asc")
//    Page<Phong> searchRoomManager4(Pageable pageable, Integer soLuongNguoi, String tenLoaiPhong);

    @Query("select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id inner join LoaiPhong l on l.id = p.loaiPhong.id " +
            "where p.trangThai = 1 and ct.trangThai = 1 and (p.giaPhong between :minGia and :maxGia) order by p.ma asc")
    Page<Phong> searchRoomManagerByPrice(Pageable pageable, BigDecimal minGia, BigDecimal maxGia);

    @Query(value = "select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id where p.trangThai = 1 and ct.trangThai = 1 and p.loaiPhong.id in (select p.loaiPhong.id from Phong p where p.id = :idPhong)")
    Page<Phong> getListSameRoom(Pageable pageable, Long idPhong);

    @Query("select p " +
            "from Phong p\n" +
            "         left join ChiTietPhong ctp on p.id = ctp.phong.id \n" +
            "         left join DatPhong dp on p.id = dp.phong.id \n" +
            "group by p.id, p.ma, p.giaPhong, p.loaiPhong.id, p.trangThai,p.image, p.sale \n" +
            "order by count(dp.phong.id) desc" )
    Page<Phong> getListTopRoomOrder(Pageable pageable);

    @Query("select p from Phong p join ChiTietPhong ctp on p.id = ctp.phong.id where p.trangThai = 1 and ctp.trangThai = 1" )
    Page<Phong> getListRoomOfFloar(Pageable pageable);

    @Query("select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id inner join LoaiPhong l on l.id = p.loaiPhong.id " +
            "where p.trangThai = 1 and ct.trangThai = 1 or l.tenLoaiPhong like concat('%', :searchInput, '%') or ct.dichVu like concat('%', :searchInput,'%') ")
    Page<Phong> searchRoomByString(Pageable pageable, String searchInput);

//    @Query(value = "SELECT * FROM phong p " +
//            "LEFT JOIN chi_tiet_phong ctp ON p.id = ctp.id_phong " +
//            "WHERE p.trang_thai = 1 AND ctp.trang_thai = 1 " +
//            "AND NOT EXISTS (" +
//            "    SELECT 1 " +
//            "    FROM dat_phong dp " +
//            "    WHERE p.id = dp.id_phong " +
//            "      AND dp.trang_thai = 1 " +
//            "      AND (" +
//            "            (CAST(:checkIn AS DATE) BETWEEN CAST(dp.check_in AS DATE) AND DATEADD(DAY, -1, CAST(dp.check_out AS DATE))) " +
//            "            OR (CAST(:checkIn AS DATE) < CAST(dp.check_in AS DATE) AND CAST(:checkIn AS DATE) < DATEADD(DAY, -1, CAST(dp.check_out AS DATE))) " +
//            "        )" +
//            ")", nativeQuery = true)
//    Page<Phong> getListRoomActive(Pageable pageable, @Param("checkIn") LocalDate checkIn);

    @Query("select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id inner join LoaiPhong l on l.id = p.loaiPhong.id " +
            "where p.trangThai = 1 and ct.trangThai = 1 and p.id not in (select d.phong.id from DatPhong d where ((cast(:checkIn as date) > cast(d.checkIn as date) and cast(:checkIn as date) < cast(d.checkOut as date)) or (cast(:checkOut as date) > cast(d.checkIn as date) and cast(:checkOut as date) < cast(d.checkOut as date))" +
            " or (cast(d.checkIn as date) > cast(:checkIn as date) and cast(d.checkIn as date) < cast(:checkOut as date)) or (cast(d.checkOut as date) > cast(:checkIn as date) and cast(d.checkOut as date) < cast(:checkOut as date)) or cast(:checkIn as date) = cast(d.checkIn as date) " +
            " or cast(:checkOut as date) = cast(d.checkOut as date)) and (d.trangThai = 1 or d.trangThai = 2 or d.trangThai = 4)) order by p.ma asc")
    Page<Phong> getListRoomActive(Pageable pageable, LocalDateTime checkIn, LocalDateTime checkOut);

    @Query("select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id")
    Page<Phong> getListRoomByTienIch(Pageable pageable, @Param("tienIch") List<String> tienIch);
//    @Query("select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id where ct.tienIch in :tienIch and ct.soLuongNguoi = :soLuongNguoi")
//    Page<Phong> getListRoomByTienIch1(Pageable pageable, @Param("tienIch") List<String> tienIch , int soLuongNguoi);
//    @Query("select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id where p.loaiPhong.tenLoaiPhong like :tenLoaiPhong")
//    Page<Phong> getListRoomByTienIch2(Pageable pageable, @Param("tienIch") List<String> tienIch , String tenLoaiPhong);
//    @Query("select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id where ct.tienIch in :tienIch and p.loaiPhong.tenLoaiPhong like :tenLoaiPhong and ct.soLuongNguoi = :soLuongNguoi")
//    Page<Phong> getListRoomByTienIch3(Pageable pageable, @Param("tienIch") List<String> tienIch , String tenLoaiPhong, int soLuongNguoi);

    @Query("select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id where p.loaiPhong.tenLoaiPhong like :tenLoaiPhong and p.trangThai = 1 and ct.trangThai = 1")
    Page<Phong> getRoomByLoaiPhong(Pageable pageable, String tenLoaiPhong);

    @Query("select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id where p.trangThai = 1 and ct.trangThai = 1 and p.id not in (select d.phong.id from DatPhong d where (d.trangThai = 1 or d.trangThai = 2) and ((cast(:checkIn as date) > cast(d.checkIn as date) and" +
            " cast(:checkIn as date) < cast(d.checkOut as date)) or (cast(:checkOut as date) > cast(d.checkIn as date) and cast(:checkOut as date) < cast(d.checkOut as date)) " +
            " or (cast(d.checkIn as date) > cast(:checkIn as date) and cast(d.checkIn as date) < cast(:checkOut as date)) or (cast(d.checkOut as date) > cast(:checkIn as date) and cast(d.checkOut as date) < cast(:checkOut as date)) or cast(:checkIn as date) = cast(d.checkIn as date) " +
            " or cast(:checkOut as date) = cast(d.checkOut as date)))")
    Page<Phong> getRoomByCheckDate(Pageable pageable, LocalDateTime checkIn, LocalDateTime checkOut);

    @Query("select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id inner join LoaiPhong l on l.id = p.loaiPhong.id " +
            "where p.trangThai = 1 and ct.trangThai = 1 and l.tenLoaiPhong like :tenLoaiPhong and p.id not in (select d.phong.id from DatPhong d where (d.trangThai = 1 or d.trangThai = 2) and ((cast(:checkIn as date) > cast(d.checkIn as date) and cast(:checkIn as date) < cast(d.checkOut as date)) or (cast(:checkOut as date) > cast(d.checkIn as date) and cast(:checkOut as date) < cast(d.checkOut as date))" +
            " or (cast(d.checkIn as date) > cast(:checkIn as date) and cast(d.checkIn as date) < cast(:checkOut as date)) or (cast(d.checkOut as date) > cast(:checkIn as date) and cast(d.checkOut as date) < cast(:checkOut as date)) or cast(:checkIn as date) = cast(d.checkIn as date)" +
            " or cast(:checkOut as date) = cast(d.checkOut as date)) and (d.trangThai = 1 or d.trangThai = 2)) order by p.ma asc")
    Page<Phong> getRoomByCheckDateandLoaiPhong(Pageable pageable, String tenLoaiPhong, LocalDateTime checkIn, LocalDateTime checkOut);

//    @Query("select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id where p.trangThai = 1 and ct.trangThai = 1 and p.id not in (select d.phong.id from DatPhong d where (d.trangThai = 1 or d.trangThai = 2) and ((cast(:checkIn as date) > cast(d.checkIn as date) and" +
//            " cast(:checkIn as date) < cast(d.checkOut as date)) or (cast(:checkOut as date) > cast(d.checkIn as date) and cast(:checkOut as date) < cast(d.checkOut as date))" +
//            " or (cast(d.checkIn as date) > cast(:checkIn as date) and cast(d.checkIn as date) < cast(:checkOut as date)) or (cast(d.checkOut as date) > cast(:checkIn as date) and cast(d.checkOut as date) < cast(:checkOut as date)) or cast(:checkIn as date) = cast(d.checkIn as date)" +
//            " or cast(:checkOut as date) = cast(d.checkOut as date)))")
//    Page<Phong> getRoomByCheckDateandTienIch(Pageable pageable,@Param("tienIch") List<String> tienIch, LocalDateTime checkIn, LocalDateTime checkOut);

//    @Query("select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id where p.trangThai = 1 and ct.trangThai = 1 and p.loaiPhong.tenLoaiPhong like :tenLoaiPhong and p.id not in (select d.phong.id from DatPhong d where (d.trangThai = 1 or d.trangThai = 2) and" +
//            " ((cast(:checkIn as date) > cast(d.checkIn as date) and cast(:checkIn as date) < cast(d.checkOut as date)) or (cast(:checkOut as date) > cast(d.checkIn as date) and" +
//            " cast(:checkOut as date) < cast(d.checkOut as date))" +
//            " or (cast(d.checkIn as date) > cast(:checkIn as date) and cast(d.checkIn as date) < cast(:checkOut as date)) or (cast(d.checkOut as date) > cast(:checkIn as date) and cast(d.checkOut as date) < cast(:checkOut as date)) or cast(:checkIn as date) = cast(d.checkIn as date)" +
//            " or cast(:checkOut as date) = cast(d.checkOut as date)))")
//    Page<Phong> getRoomByCheckDateandAll(Pageable pageable,@Param("tienIch") List<String> tienIch, String tenLoaiPhong, LocalDateTime checkIn, LocalDateTime checkOut);

}