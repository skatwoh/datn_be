package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.payload.PhongResponse1;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PhongRepository extends JpaRepository<Phong, Long> {

    @Query("select new be.bds.bdsbes.payload.PhongResponse1(p.id, p.ma, p.giaPhong, p.trangThai, l.id, l.tenLoaiPhong) from Phong p inner join p.loaiPhong l where p.id = ?1")
    PhongResponse1 get(Long id);

    @Query(value = "select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id where p.trangThai = 1 and ct.trangThai = 1")
    Page<Phong> getListRoom(Pageable pageable);

    @Query("select new be.bds.bdsbes.payload.PhongResponse1(p.id, p.ma, p.giaPhong, p.trangThai, l.id, l.tenLoaiPhong) from Phong p inner join p.loaiPhong l")
    List<PhongResponse1> singleListRoom();

    @Query("select p from Phong p where p.ma like CONCAT('%', ?1, '%') or p.loaiPhong.tenLoaiPhong like CONCAT('%',?1, '%')")
    Page<Phong> searchRoom(Pageable pageable, String searchInput);


    @Transactional
    @Modifying
    @Query("UPDATE Phong p SET p.trangThai = :trangThai WHERE p.id = :id")
    Integer updateTrangThaiById(int trangThai, Long id);

    @Query("select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id inner join LoaiPhong l on l.id = p.loaiPhong.id " +
            "where p.trangThai = 1 and ct.trangThai = 1 and l.tenLoaiPhong = :tenLoaiPhong and (p.id not in (select d.phong.id from DatPhong d where (:checkIn between d.checkIn and d.checkOut) or (:checkOut between d.checkIn and d.checkOut) and d.trangThai = 1 )) order by p.ma asc")
    Page<Phong> searchRoomManager(Pageable pageable, String tenLoaiPhong, LocalDateTime checkIn, LocalDateTime checkOut);

    @Query("select p from Phong p inner join ChiTietPhong ct on p.id = ct.phong.id inner join LoaiPhong l on l.id = p.loaiPhong.id " +
            "where p.trangThai = 1 and ct.trangThai = 1 and l.tenLoaiPhong = :tenLoaiPhong order by p.ma asc")
    Page<Phong> searchRoomManager2(Pageable pageable, String tenLoaiPhong);


}