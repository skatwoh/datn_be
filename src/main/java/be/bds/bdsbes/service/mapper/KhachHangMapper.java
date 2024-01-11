package be.bds.bdsbes.service.mapper;


import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.payload.KhachHangResponse1;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface KhachHangMapper extends EntityMapper<KhachHangResponse1, KhachHang>{
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)

    @Mapping(target = "rank" , source = "theThanhVien.capBac")
    @Mapping(target = "giamGia", source = "theThanhVien.giamGia")
    KhachHangResponse1 toDto(KhachHang khachHang);
}
