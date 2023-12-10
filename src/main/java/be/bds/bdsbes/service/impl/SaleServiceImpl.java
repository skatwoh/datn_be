package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.entities.Sale;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.SaleResponse;
import be.bds.bdsbes.repository.SaleRepository;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;
import be.bds.bdsbes.service.iService.ISaleService;
import be.bds.bdsbes.service.mapper.SaleMapper;
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

@Slf4j
@Service
public class SaleServiceImpl implements ISaleService {

    @Autowired
    SaleRepository saleRepository;

    @Autowired
    SaleMapper saleMapper;

    @Override
    public PagedResponse<SaleResponse> getSale(int page, int size) throws ServiceException {
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

        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.ASC, "id");
        Page<Sale> entities = saleRepository.findAll(pageable);

        List<SaleResponse> dtos = this.saleMapper.toDtoList(entities.getContent());

        return new PagedResponse<>(
                dtos,
                page,
                size,
                entities.getTotalElements(),
                entities.getTotalPages(),
                entities.isLast(),
                entities.getSort().toString()
        );
    }

    @Override
    public Integer updateTrangThai(Long id) {
        Sale sale = saleRepository.findById(id).get();
        if (sale.getTrangThai() == 0) {
            return saleRepository.updateTrangThaiById(1, id);
        }
        if (sale.getTrangThai() == 1) {
            return saleRepository.updateTrangThaiById(0, id);
        }
        return null;
    }

    @Override
    public SaleResponse get(Long id) {
        return saleRepository.get(id);
    }

    @Override
    public SaleResponse getSale() {
        return saleRepository.getSale();
    }
}
