package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.entities.ChiTietPhong;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.ChiTietPhongResponse1;
import be.bds.bdsbes.payload.PhongResponse1;
import be.bds.bdsbes.service.dto.ChiTietPhongDTO;
import be.bds.bdsbes.utils.dto.PagedResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IChiTietPhongService {

    List<ChiTietPhong> getList();

    PagedResponse<ChiTietPhongResponse1> searchRoom(int page, int size, String searchInput) throws ServiceException;

    ChiTietPhongResponse1 get(Long id);

    ChiTietPhong create(ChiTietPhongDTO chiTietPhongDTO);

    ChiTietPhong update(ChiTietPhongDTO chiTietPhongDTO, Long id);

    Integer updateTrangThai(Long id);

    PagedResponse<ChiTietPhongResponse1> getChiTietPhong(int page, int size) throws ServiceException;

    PagedResponse<ChiTietPhongResponse1> getChiTietPhongSortbyId(int page, int size) throws ServiceException;

    ChiTietPhongResponse1 getCTP(Long idPhong);

    PagedResponse<ChiTietPhongResponse1> searchRoomBySoNguoi(int page, int size, Integer soLuongNguoi, Integer soPhong);
}
