package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.DatPhong;
import be.bds.bdsbes.service.dto.response.DatPhongResponse;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface DatPhongMapper extends EntityMapper<DatPhongResponse, DatPhong> {

    @Mapping(target = "maKhachHang", source = "user.khachHang.ma")
    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "sdt", source = "user.sdt")
    @Mapping(target = "tenPhong", source = "phong.ma")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DatPhongResponse toDto(DatPhong datPhong);
}
