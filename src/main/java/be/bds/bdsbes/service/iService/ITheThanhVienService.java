package be.bds.bdsbes.service.iService;

import be.bds.bdsbes.entities.TheThanhVien;
import be.bds.bdsbes.service.dto.TheThanhVienDTO;
import be.bds.bdsbes.service.dto.response.TheThanhVienResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITheThanhVienService {

    List<TheThanhVienResponse> getList();

    Page<TheThanhVien> getPage(Integer page);

    TheThanhVien getOne(Long id);

    TheThanhVien add(TheThanhVienDTO theThanhVienDTO);

    TheThanhVien update(TheThanhVienDTO theThanhVienDTO, Long id);
}
