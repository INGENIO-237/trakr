package app.vercel.ingenio_theta.trakr.users;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import app.vercel.ingenio_theta.trakr.users.dtos.CreateUserDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;

@Service
public class UserMapper {
    User toUser(CreateUserDto dto) {
        return User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(dto.password())
                .build();
    }

    UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .country(user.getCountry())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    List<UserResponse> toUserResponseList(Page<User> users) {
        return users.stream().map(user -> toUserResponse(user)).toList();
    }
}
