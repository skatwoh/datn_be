package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.entities.TaiSan;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.DichVuResponse1;
import be.bds.bdsbes.payload.TaiSanResponse1;
import be.bds.bdsbes.service.dto.TaiSanDTO;
import be.bds.bdsbes.utils.dto.PagedResponse;
import org.springframework.data.domain.Page;
import be.bds.bdsbes.service.dto.response.TaiSanResponse;
import java.util.List;

public interface ITaiSanService {
    List<TaiSanResponse> getList();

    Page<TaiSan> getPage(Integer page);

    TaiSanResponse1 getTaiSan(Long id);

    TaiSan create(TaiSanDTO taiSanDTO);

    TaiSan update(TaiSanDTO taiSanDTO, Long id);

    public PagedResponse<TaiSanResponse1> getAccounts(int page, int size) throws ServiceException;
}
