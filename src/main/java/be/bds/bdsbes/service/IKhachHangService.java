package be.bds.bdsbes.service;

import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.service.dto.KhachHangDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IKhachHangService {

    List<KhachHang> getList();

    Page<KhachHang> getPage(Integer page);

    KhachHang getOne(Long id);

    KhachHang create(KhachHangDTO khachHangDTO);

    KhachHang update(KhachHangDTO khachHangDTO, Long id);
}
