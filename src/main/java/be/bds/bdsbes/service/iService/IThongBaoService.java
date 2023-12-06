package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.service.dto.ThongBaoDTO;
import be.bds.bdsbes.service.dto.response.ThongBaoResponse;
import be.bds.bdsbes.utils.dto.PagedResponse;

import java.util.List;

public interface IThongBaoService {

    List<ThongBaoResponse> getAllThongBao(Long userId);

    PagedResponse<ThongBaoResponse> listThongBao(int page, int size, Long userId) throws ServiceException;

    Boolean create(ThongBaoDTO thongBaoDTO);
}
