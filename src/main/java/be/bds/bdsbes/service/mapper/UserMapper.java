package be.bds.bdsbes.service.mapper;

import be.bds.bdsbes.domain.User;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import be.bds.bdsbes.payload.UserProfileResponse;

@Mapper(componentModel = "spring", builder = @Builder(disableBuilder = true))
public interface UserMapper extends EntityMapper<UserProfileResponse, User>{
    @Named("mapToEntityWithoutData")

    User toEntity(UserProfileResponse payload);
}