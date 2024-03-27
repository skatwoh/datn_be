package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.entities.BaoTri;
import be.bds.bdsbes.entities.ChiTietPhong;
import be.bds.bdsbes.entities.QuanLyDoiTac;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.BaoTriResponse1;
import be.bds.bdsbes.payload.ChiTietPhongResponse1;
import be.bds.bdsbes.payload.QuanLyDoiTacResponse1;
import be.bds.bdsbes.resource.BaoTriController;
import be.bds.bdsbes.service.dto.BaoTriDto;
import be.bds.bdsbes.service.dto.QuanLyDoiTacDTO;
import be.bds.bdsbes.utils.dto.PagedResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface IBaoTriService {
    List<BaoTri> getList();

    Page<BaoTri> getPage(Integer page);

    BaoTriResponse1 getOne(Long id);

    List<ChiTietPhongResponse1> getListCTP();
    BaoTri create(BaoTriDto baoTriDto) throws ServiceException;
    PagedResponse<BaoTriResponse1> searchMaintenance(int page, int size, String searchInput) throws ServiceException;
    BaoTri update(BaoTriDto baoTriDto, Long id);
    Integer updateTrangThaiById( Long id);
    PagedResponse<BaoTriResponse1> getBaoTri(int page, int size) throws ServiceException;

    List<BaoTriResponse1> getListByCTPhong(int page, int size, Long id, String ghiChu) throws ServiceException;
}
