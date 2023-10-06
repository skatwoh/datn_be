package be.bds.bdsbes.service.mapper;


import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.payload.KhachHangResponse1;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface KhachHangMapper extends EntityMapper<KhachHangResponse1, KhachHang>{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    KhachHang partialUpdate(KhachHangResponse1 khachHangResponse1, @MappingTarget KhachHang khachHang);
}
