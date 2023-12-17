package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.FeedBack;
import be.bds.bdsbes.entities.ThongBao;
import be.bds.bdsbes.service.dto.response.FeedbackResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface FeedbackMapper extends EntityMapper<FeedbackResponse, FeedBack>{
    @Mapping(target = "tenKhachHang", source = "khachHang.hoTen")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FeedbackResponse toDto(FeedBack feedBack);
}