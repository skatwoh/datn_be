package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.TaiKhoan;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.TaiKhoanResponse1;
import be.bds.bdsbes.repository.TaiKhoanRepository;
import be.bds.bdsbes.service.iService.ITaiKhoanService;
import be.bds.bdsbes.service.dto.LoginDTO;
import be.bds.bdsbes.service.dto.TaiKhoanDTO;
import be.bds.bdsbes.service.dto.response.TaiKhoanResponse;
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
@Service("taiKhoanServiceImpl")
public class TaiKhoanServiceImpl implements ITaiKhoanService {

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private TaiKhoanMapper taiKhoanMapper;

    @Override
    public List<TaiKhoan> getList() {
        return taiKhoanRepository.findAll();
    }

    @Override
    public List<TaiKhoanResponse> getAllTaiKhoan() {
        return taiKhoanRepository.getAllTaiKhoan();
    }

    @Override
    public Page<TaiKhoan> getPage(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return taiKhoanRepository.findAll(pageable);
    }

    @Override
    public TaiKhoan getOne(Long id) {
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanRepository.findById(id);
        if(taiKhoanOptional.isPresent()){
            TaiKhoan taiKhoan = taiKhoanOptional.get();
            return taiKhoan;
        }
        return null;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public TaiKhoan get(Long id) {
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanRepository.get(id);
        if(taiKhoanOptional.isPresent()){
            TaiKhoan taiKhoan = taiKhoanOptional.get();
            return taiKhoan;
        }
        return null;
    }

    @Override
    public TaiKhoan add(TaiKhoanDTO taiKhoanDTO) {
        TaiKhoan taiKhoan = taiKhoanDTO.dto(new TaiKhoan());
        return taiKhoanRepository.save(taiKhoan);
    }

    @Override
    public TaiKhoan update(TaiKhoanDTO taiKhoanDTO, Long id) {
        Optional<TaiKhoan> taiKhoanOptional = taiKhoanRepository.findById(id);
        if(taiKhoanOptional.isPresent()){
            TaiKhoan taiKhoan = taiKhoanDTO.dto(taiKhoanOptional.get());
            return taiKhoan;
        }
        return null;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        TaiKhoan taiKhoan = taiKhoanRepository.findByEmail(loginDTO.getEmail());
        if (loginDTO.getMatKhau().equals(taiKhoan.getMatKhau())) {
            return "Đăng nhập thành công";
        } else {
            return "Đăng nhập thất bại";
        }
    }

    /**
     * @param page
     * @param size
     * @return
     * @throws ServiceException
     */
    @Override
    public PagedResponse<TaiKhoanResponse1> getAccounts(int page, int size) throws ServiceException {
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
        Page<TaiKhoan> entities = taiKhoanRepository.findAll(pageable);

        List<TaiKhoanResponse1> dtos = this.taiKhoanMapper.toDtoList(entities.getContent());

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
}
