package be.bds.bdsbes.service;

import be.bds.bdsbes.entities.BaoTri;
import be.bds.bdsbes.entities.QuanLyDoiTac;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.BaoTriResponse1;
import be.bds.bdsbes.payload.QuanLyDoiTacResponse1;
import be.bds.bdsbes.resource.BaoTriController;
import be.bds.bdsbes.service.dto.BaoTriDto;
import be.bds.bdsbes.service.dto.QuanLyDoiTacDTO;
import be.bds.bdsbes.utils.dto.PagedResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface IBaoTriService {
    List<BaoTri> getList();

    Page<BaoTri> getPage(Integer page);

    BaoTri getOne(Long id);

    BaoTri create(BaoTriDto baoTriDto);

    BaoTri update(BaoTriDto baoTriDto, Long id);

    PagedResponse<BaoTriResponse1> getBaoTri(int page, int size) throws ServiceException;
}
