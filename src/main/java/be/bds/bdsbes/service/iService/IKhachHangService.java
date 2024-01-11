package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.KhachHangResponse1;
import be.bds.bdsbes.service.dto.KhachHangDTO;
import be.bds.bdsbes.utils.dto.PagedResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IKhachHangService {

    List<KhachHang> getList();

    Page<KhachHang> getPage(Integer page);

    KhachHangResponse1 getOne(Long id);

    KhachHang create(KhachHangDTO khachHangDTO);

    KhachHang update(KhachHangDTO khachHangDTO, Long id);

    PagedResponse<KhachHangResponse1> getKhachHang(int page, int size) throws ServiceException;

    Boolean createOrUpdate(KhachHangDTO khachHangDTO) throws ServiceException;

    Long findIdByCCCD(String cccd);

    KhachHangResponse1 getKhachHangbyUser(Long id);
}
