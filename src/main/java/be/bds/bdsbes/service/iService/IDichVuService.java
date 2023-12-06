package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.entities.DichVu;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.DichVuResponse1;
import be.bds.bdsbes.service.dto.DichVuDTO;
import be.bds.bdsbes.service.dto.response.DichVuResponse;
import be.bds.bdsbes.utils.dto.PagedResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDichVuService {
    List<DichVuResponse> getList();

    Page<DichVu> getPage(Integer page);

    DichVuResponse1 getDichVu(Long id);

    DichVu create(DichVuDTO dichVuDTO);

    DichVu update(DichVuDTO dichVuDTO, Long id);
    Integer updateTrangThai(Long id);

    public PagedResponse<DichVuResponse1> getAccounts(int page, int size) throws ServiceException;
    PagedResponse<DichVuResponse1> searchRoomService(int page, int size, String searchInput) throws ServiceException;
}
