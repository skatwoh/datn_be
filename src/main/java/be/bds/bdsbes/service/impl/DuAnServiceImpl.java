package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.DuAn;
import be.bds.bdsbes.entities.TaiKhoan;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.DuAnResponse1;
import be.bds.bdsbes.payload.TaiKhoanResponse1;
import be.bds.bdsbes.repository.DuAnRepository;
import be.bds.bdsbes.service.IDuAnService;
import be.bds.bdsbes.service.dto.DuAnDTO;
import be.bds.bdsbes.service.mapper.DuAnMapper;
import be.bds.bdsbes.service.mapper.TaiKhoanMapper;
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
@Slf4j
@Service("DuAnServiceImpl")
public class DuAnServiceImpl implements IDuAnService {
    @Autowired
    DuAnRepository duAnRepository;
    @Autowired
    private DuAnMapper duAnMapper;

    @Override
    public List<DuAn> getList() {
        return duAnRepository.findAll();
    }

    @Override
    public Page<DuAn> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return duAnRepository.findAll(pageable);
    }

    @Override
    public DuAn getOne(Long id) {
        Optional<DuAn> duAnOptional = duAnRepository.findById(id);
        if(duAnOptional.isPresent()){
            DuAn duAn = duAnOptional.get();
            return duAn;
        }
        return null;
    }

    @Override
    public DuAn create(DuAnDTO duAnDTO) {
        DuAn duAn = duAnDTO.dto(new DuAn());
        return duAnRepository.save(duAn);
    }

    @Override
    public DuAn update(DuAnDTO duAnDTO, Long id) {
        Optional<DuAn> duAnOptional = duAnRepository.findById(id);
        if(duAnOptional.isPresent()){
           DuAn duAn = duAnDTO.dto(duAnOptional.get());
            return duAnRepository.save(duAn);
        }
        return null;
    }
    /**
     * @param page
     * @param size
     * @return
     * @throws ServiceException
     */
    @Override
    public PagedResponse<DuAnResponse1> getAccounts(int page, int size) throws ServiceException {
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
        Page<DuAn> entities = duAnRepository.findAll(pageable);

        return new PagedResponse<>(
                this.duAnMapper.toDtoList(entities.getContent()),
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }
}
