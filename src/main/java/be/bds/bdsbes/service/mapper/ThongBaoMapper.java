package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.BaoTri;
import be.bds.bdsbes.entities.ThongBao;
import be.bds.bdsbes.payload.BaoTriResponse1;
import be.bds.bdsbes.service.dto.response.ThongBaoResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ThongBaoMapper extends EntityMapper<ThongBaoResponse, ThongBao>{
    @Mapping(target = "userId", source = "user.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ThongBaoResponse toDto(ThongBao thongBao);
}