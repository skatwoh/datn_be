package be.bds.bdsbes.service;

import be.bds.bdsbes.entities.TaiKhoan;
import be.bds.bdsbes.service.dto.TaiKhoanDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITaiKhoanService {

    List<TaiKhoan> getList();

    Page<TaiKhoan> getPage(Integer page);

    TaiKhoan getOne(Long id);

    TaiKhoan add(TaiKhoanDTO taiKhoanDTO);

    TaiKhoan update(TaiKhoanDTO taiKhoanDTO, Long id);
}
