package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.domain.User;
import be.bds.bdsbes.payload.SignUpRequest;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface SignUpMapper extends EntityMapper<SignUpRequest, User> {
}