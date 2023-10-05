package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.ChiTietPhong;
import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.payload.ChiTietPhongResponse1;
import be.bds.bdsbes.payload.KhachHangResponse1;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ChiTietPhongMapper extends EntityMapper<ChiTietPhongResponse1, ChiTietPhong>{

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ChiTietPhong partialUpdate(ChiTietPhongResponse1 chiTietPhongResponse1, @MappingTarget ChiTietPhong chiTietPhong);

}
