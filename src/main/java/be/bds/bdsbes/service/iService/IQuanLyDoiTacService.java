package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.entities.QuanLyDoiTac;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.BaoTriResponse1;
import be.bds.bdsbes.payload.KhachHangResponse1;
import be.bds.bdsbes.payload.QuanLyDoiTacResponse1;
import be.bds.bdsbes.service.dto.KhachHangDTO;
import be.bds.bdsbes.service.dto.QuanLyDoiTacDTO;
import be.bds.bdsbes.utils.dto.PagedResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface IQuanLyDoiTacService {
    List<QuanLyDoiTac> getList();

    Page<QuanLyDoiTac> getPage(Integer page);

    QuanLyDoiTac getOne(Long id);

    QuanLyDoiTac create(QuanLyDoiTacDTO quanLyDoiTacDTO);

    QuanLyDoiTac update(QuanLyDoiTacDTO quanLyDoiTacDTO, Long id);
    Integer updateTrangThaiById( Long id);
    PagedResponse<QuanLyDoiTacResponse1> searchPartner(int page, int size, String searchInput) throws ServiceException;
    PagedResponse<QuanLyDoiTacResponse1> getQuanLyDoiTac(int page, int size) throws ServiceException;
}
