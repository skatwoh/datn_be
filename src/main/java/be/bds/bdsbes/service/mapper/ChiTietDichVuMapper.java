package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.ChiTietDichVu;
import be.bds.bdsbes.entities.DichVu;
import be.bds.bdsbes.entities.DuAn;
import be.bds.bdsbes.payload.ChiTietDichVuResponse1;
import be.bds.bdsbes.payload.DichVuResponse1;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ChiTietDichVuMapper extends EntityMapper<ChiTietDichVuResponse1, ChiTietDichVu> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ChiTietDichVu partialUpdate(ChiTietDichVuResponse1 chiTietDichVuResponse1, @MappingTarget ChiTietDichVu chiTietDichVu);
}
