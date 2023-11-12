package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.BaoTri;
import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.payload.BaoTriResponse1;
import be.bds.bdsbes.payload.KhachHangResponse1;
import be.bds.bdsbes.service.dto.BaoTriDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BaoTriMapper extends EntityMapper<BaoTriResponse1, BaoTri>{
    @Mapping(target = "idChiTietPhong", source = "chiTietPhong.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BaoTriResponse1 toDto(BaoTri baoTri);
}