package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.KhachHang;
import be.bds.bdsbes.entities.Phong;
import be.bds.bdsbes.entities.QuanLyDoiTac;
import be.bds.bdsbes.payload.KhachHangResponse1;
import be.bds.bdsbes.payload.PhongResponse1;
import be.bds.bdsbes.payload.QuanLyDoiTacResponse1;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface QuanLyDoiTacMapper extends EntityMapper<QuanLyDoiTacResponse1, QuanLyDoiTac> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    QuanLyDoiTac partialUpdate(QuanLyDoiTacResponse1 quanLyDoiTacResponse1, @MappingTarget QuanLyDoiTac quanLyDoiTac);
}
