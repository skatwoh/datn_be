package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.entities.CoSoVatChat;
import be.bds.bdsbes.entities.TaiSan;
import be.bds.bdsbes.payload.CoSoVatChatResponse1;
import be.bds.bdsbes.payload.TaiSanResponse1;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CoSoVatChatMapper extends EntityMapper<CoSoVatChatResponse1, CoSoVatChat> {
    CoSoVatChat partialUpdate(CoSoVatChatResponse1 coSoVatChatResponse1, @MappingTarget CoSoVatChat coSoVatChat);
}
