package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository("datPhongRepository")
public interface DatPhongRepository extends JpaRepository<DatPhong, Long> {

    @Query("select new be.bds.bdsbes.service.dto.response.DatPhongResponse(d.id, d.ma, k.id, k.ma, k.sdt, u.name, v.id, v.ma," +
            " v.giamGia, d.ngayDat, d.checkIn, d.checkOut, d.soNguoi, d.ghiChu, d.trangThai, d.phong.ma) " +
            "from DatPhong d join d.user u join u.khachHang k left join d.voucher v")
    List<DatPhongResponse> getAllDatPhong();

    @Query("SELECT CASE WHEN COUNT(dp) > 0 THEN true ELSE false END " +
            "FROM DatPhong dp " +
            "WHERE dp.phong.id = :idPhong and cast(dp.checkIn as date) = cast(:checkIn as date) ")
    Boolean validateCheckIn(@Param("idPhong") Long idPhong, @Param("checkIn") LocalDateTime checkIn);


}