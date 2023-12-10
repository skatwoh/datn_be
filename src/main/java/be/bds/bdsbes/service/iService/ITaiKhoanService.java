package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.entities.TaiKhoan;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.TaiKhoanResponse1;
import be.bds.bdsbes.payload.UserProfileResponse;
import be.bds.bdsbes.service.dto.LoginDTO;
import be.bds.bdsbes.service.dto.TaiKhoanDTO;
import be.bds.bdsbes.service.dto.response.TaiKhoanResponse;
import be.bds.bdsbes.utils.dto.PagedResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITaiKhoanService {

    List<TaiKhoan> getList();

    List<TaiKhoanResponse> getAllTaiKhoan();

    Page<TaiKhoan> getPage(Integer page);

    TaiKhoan getOne(Long id);

    TaiKhoan get(Long id);

    TaiKhoan add(TaiKhoanDTO taiKhoanDTO);

    TaiKhoan update(TaiKhoanDTO taiKhoanDTO, Long id);
    String login(LoginDTO loginDTO);

    public PagedResponse<TaiKhoanResponse1> getAccounts(int page, int size) throws ServiceException;
}
