package be.bds.bdsbes.service;

import be.bds.bdsbes.entities.TheThanhVien;
import be.bds.bdsbes.service.dto.TheThanhVienDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TheThanhVienService {

    List<TheThanhVien> getList();

    Page<TheThanhVien> getPage(Integer page);

    TheThanhVien getOne(Long id);

    TheThanhVien add(TheThanhVienDTO theThanhVienDTO);

    TheThanhVien update(TheThanhVienDTO theThanhVienDTO, Long id);
}
