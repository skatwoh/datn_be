package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.entities.LoaiPhong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.LoaiPhongResponse1;
import be.bds.bdsbes.service.dto.LoaiPhongDTO;
import be.bds.bdsbes.service.dto.response.LoaiPhongResponse;
import be.bds.bdsbes.utils.dto.PagedResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

public interface ILoaiPhongService {

    List<LoaiPhong> getList();

    List<LoaiPhongResponse1> singleListRoomType();
    Page<LoaiPhong> getPage(Integer page);

    LoaiPhongResponse1 get(Long id);

    LoaiPhong create(LoaiPhongDTO loaiPhongDTO);

    LoaiPhong update(LoaiPhongDTO loaiPhongDTO, Long id);

    PagedResponse<LoaiPhongResponse1> getLoaiPhong(int page, int size) throws ServiceException;

    List<LoaiPhongResponse1> listLoaiPhongBySoNguoiAndSoPhong1(int soPhong, int soNguoi, LocalDateTime checkIn, LocalDateTime checkOut);

    List<LoaiPhongResponse1> listLoaiPhongBySoNguoiAndSoPhong2(int soPhong, int soNguoi, LocalDateTime checkIn, LocalDateTime checkOut);
}
