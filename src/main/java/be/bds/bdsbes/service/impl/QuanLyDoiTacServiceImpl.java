package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.entities.QuanLyDoiTac;
import be.bds.bdsbes.entities.TheThanhVien;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.KhachHangResponse1;
import be.bds.bdsbes.payload.QuanLyDoiTacResponse1;
import be.bds.bdsbes.repository.QuanLyDoiTacRepository;
import be.bds.bdsbes.service.IQuanLyDoiTacService;
import be.bds.bdsbes.service.dto.KhachHangDTO;
import be.bds.bdsbes.service.dto.QuanLyDoiTacDTO;
import be.bds.bdsbes.service.mapper.QuanLyDoiTacMapper;
import be.bds.bdsbes.utils.AppConstantsUtil;
import be.bds.bdsbes.utils.ServiceExceptionBuilderUtil;
import be.bds.bdsbes.utils.ValidationErrorUtil;
import be.bds.bdsbes.utils.dto.KeyValue;
import be.bds.bdsbes.utils.dto.PagedResponse;
import be.bds.bdsbes.utils.dto.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service("quanLyDoiTacServiceImpl")
public class QuanLyDoiTacServiceImpl implements IQuanLyDoiTacService {

    @Autowired
    QuanLyDoiTacRepository quanLyDoiTacRepository;
    @Autowired
    QuanLyDoiTacMapper quanLyDoiTacMapper;

    @Override
    public List<QuanLyDoiTac> getList() {
        return quanLyDoiTacRepository.findAll();
    }

    @Override
    public Page<QuanLyDoiTac> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return quanLyDoiTacRepository.findAll(pageable);
    }

    @Override
    public QuanLyDoiTac getOne(Long id) {
        Optional<QuanLyDoiTac> quanLyDoiTacOptional = quanLyDoiTacRepository.findById(id);
        if (quanLyDoiTacOptional.isPresent()) {
            QuanLyDoiTac quanLyDoiTac = quanLyDoiTacOptional.get();
            return quanLyDoiTac;
        }
        return null;
    }

    @Override
    public QuanLyDoiTac create(QuanLyDoiTacDTO quanLyDoiTacDTO) {
        QuanLyDoiTac quanLyDoiTac = quanLyDoiTacDTO.dto(new QuanLyDoiTac());

        return quanLyDoiTacRepository.save(quanLyDoiTac);
    }

    @Override
    public QuanLyDoiTac update(QuanLyDoiTacDTO quanLyDoiTacDTO, Long id) {
        Optional<QuanLyDoiTac> quanLyDoiTacOptional = quanLyDoiTacRepository.findById(id);
        if (quanLyDoiTacOptional.isPresent()) {
            QuanLyDoiTac quanLyDoiTac = quanLyDoiTacDTO.dto(quanLyDoiTacOptional.get());
            return quanLyDoiTacRepository.save(quanLyDoiTac);
        }
        return null;
    }

    @Override
    public PagedResponse<QuanLyDoiTacResponse1> getQuanLyDoiTac(int page, int size) throws ServiceException {
        if (page <= 0) {
            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("page", ValidationErrorUtil.Invalid))
                    .build();
        }

        if (size > AppConstantsUtil.MAX_PAGE_SIZE) {
            List<KeyValue> params = new ArrayList<>();
            params.add(new KeyValue("max", String.valueOf(AppConstantsUtil.MAX_PAGE_SIZE)));

            throw ServiceExceptionBuilderUtil.newBuilder()
                    .addError(new ValidationErrorResponse("pageSize", ValidationErrorUtil.Invalid, params))
                    .build();
        }

        // Retrieve all entities
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.ASC, "id");
        Page<QuanLyDoiTac> entities = quanLyDoiTacRepository.findAll(pageable);

        return new PagedResponse<>(
                this.quanLyDoiTacMapper.toDtoList(entities.getContent()),
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }
}
