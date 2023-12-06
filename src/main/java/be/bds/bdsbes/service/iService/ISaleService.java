package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.SaleResponse;
import be.bds.bdsbes.utils.dto.PagedResponse;

public interface ISaleService {
    PagedResponse<SaleResponse> getSale(int page, int size) throws ServiceException;

    Integer updateTrangThai(Long id);

    SaleResponse get(Long id);

    SaleResponse getSale();
}
