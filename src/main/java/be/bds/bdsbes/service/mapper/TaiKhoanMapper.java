package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.ChiTietPhong;
import be.bds.bdsbes.entities.TaiKhoan;
import be.bds.bdsbes.payload.ChiTietPhongResponse1;
import be.bds.bdsbes.payload.TaiKhoanResponse1;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface TaiKhoanMapper extends EntityMapper<TaiKhoanResponse1, TaiKhoan> {

    @Mapping(target = "maKhachHang", source = "khachHang.ma")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TaiKhoanResponse1 toDto(TaiKhoan taiKhoan);
}