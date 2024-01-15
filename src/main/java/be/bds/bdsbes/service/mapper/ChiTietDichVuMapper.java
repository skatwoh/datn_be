package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.ChiTietDichVu;
import be.bds.bdsbes.payload.ChiTietDichVuResponse1;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface ChiTietDichVuMapper extends EntityMapper<ChiTietDichVuResponse1, ChiTietDichVu> {

//    @Mapping(target = "idDichVu", source = "dichVu.id")
//    @Mapping(target = "idDatPhong", source = "datPhong.id")
    @Mapping(target = "tenDichVu", source = "dichVu.tenDichVu")
    @Mapping(target = "giaDichVu", source = "dichVu.giaDichVu")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ChiTietDichVuResponse1 toDto(ChiTietDichVu chiTietDichVu);
}
