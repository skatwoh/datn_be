package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.entities.DuAn;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.DuAnResponse1;
import be.bds.bdsbes.service.dto.DuAnDTO;
import be.bds.bdsbes.utils.dto.PagedResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IDuAnService {
    List<DuAn> getList();

    Page<DuAn> getPage(Integer page);

    DuAnResponse1 getDuAn(Long id);

    DuAn create(DuAnDTO duAnDTO);

    DuAn update(DuAnDTO duAnDTO, Long id);

    Integer updateTrangThai(Long id);

    public PagedResponse<DuAnResponse1> getAccounts(int page, int size) throws ServiceException;
    PagedResponse<DuAnResponse1> searchProjetc(int page, int size, String searchInput) throws ServiceException;

}
