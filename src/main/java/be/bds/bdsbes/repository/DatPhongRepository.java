package be.bds.bdsbes.repository;

import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("datPhongRepository")
public interface DatPhongRepository extends JpaRepository<DatPhong, Long> {

    @Query("select new be.bds.bdsbes.service.dto.response.DatPhongResponse(d.id, k.id, k.ma, k.sdt, v.id, v.ma, v.giamGia, d.ngayDat, d.checkIn, d.checkOut, d.soNguoi, d.ghiChu, d.trangThai) " +
            "from DatPhong d join d.user u join u.khachHang k left join d.voucher v")
    List<DatPhongResponse> getAllDatPhong();
}