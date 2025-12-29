package app.vercel.ingenio_theta.trakr.auth;

import org.mapstruct.Mapper;

import app.vercel.ingenio_theta.trakr.auth.dtos.RegisterDto;
import app.vercel.ingenio_theta.trakr.users.dtos.CreateUserDto;

@Mapper(componentModel = "spring")
public interface AuthMapper {
    CreateUserDto toCreateUserDto(RegisterDto dto);
}
