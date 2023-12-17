package be.bds.bdsbes.service.impl;

import be.bds.bdsbes.entities.ChiTietPhong;
import be.bds.bdsbes.entities.FeedBack;
import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.entities.ThongBao;
import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.repository.ChiTietPhongRepository;
import be.bds.bdsbes.repository.FeedbackRepository;
import be.bds.bdsbes.repository.KhachHangRepository;
import be.bds.bdsbes.service.dto.FeedbackDTO;
import be.bds.bdsbes.service.dto.response.FeedbackResponse;
import be.bds.bdsbes.service.dto.response.ThongBaoResponse;
import be.bds.bdsbes.service.iService.IFeedBackService;
import be.bds.bdsbes.service.mapper.FeedbackMapper;
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
public class FeedbackServiceImpl implements IFeedBackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    KhachHangRepository khachHangRepository;

    @Autowired
    ChiTietPhongRepository chiTietPhongRepository;

    @Autowired
    FeedbackMapper feedbackMapper;

    @Override
    public Boolean create(FeedbackDTO feedbackDTO) {
        FeedBack feedBack = new FeedBack();
        Long idChiTietPhong = chiTietPhongRepository.findByIdCTP(feedbackDTO.getIdChiTietPhong());
        feedBack.setChiTietPhong(ChiTietPhong.builder().id(idChiTietPhong).build());
        Long idKH = khachHangRepository.findByI(feedbackDTO.getIdKhachHang());
        feedBack.setKhachHang(KhachHang.builder().id(idKH).build());
        feedBack.setMoTa(feedbackDTO.getMoTa());
        feedBack.setTrangThai(feedbackDTO.getTrangThai());
        feedbackRepository.save(feedBack);
        return true;
    }

    @Override
    public PagedResponse<FeedbackResponse> listFeedback(int page, int size, Long idChiTietPhong) throws ServiceException {
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
        Pageable pageable = PageRequest.of((page - 1), size, Sort.Direction.DESC, "id");
        Long idChiTietPhongs = chiTietPhongRepository.findByIdCTP(idChiTietPhong);
        Page<FeedBack> entities = feedbackRepository.listAll(pageable, idChiTietPhongs);

        List<FeedbackResponse> dtos = this.feedbackMapper.toDtoList(entities.getContent());
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
    public Long count() {
        return this.feedbackRepository.count();
    }


}
