package app.vercel.ingenio_theta.trakr.users;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

import app.vercel.ingenio_theta.trakr.users.dtos.CreateUserDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UpdateUserDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "budgets", ignore = true)
    User toEntity(CreateUserDto dto);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "budgets", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntity(UpdateUserDto dto, @MappingTarget User user);

    UserResponse toResponse(User user);

    default Page<UserResponse> toResponses(Page<User> page) {
        return page.map(this::toResponse);
    }
}