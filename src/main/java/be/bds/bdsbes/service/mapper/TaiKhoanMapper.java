package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.TaiKhoan;
import be.bds.bdsbes.payload.TaiKhoanResponse1;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TaiKhoanMapper extends EntityMapper<TaiKhoanResponse1, TaiKhoan> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TaiKhoan partialUpdate(TaiKhoanResponse1 taiKhoanResponse1, @MappingTarget TaiKhoan taiKhoan);
}