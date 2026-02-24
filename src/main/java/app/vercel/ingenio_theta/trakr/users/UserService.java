package app.vercel.ingenio_theta.trakr.users;

import org.springframework.data.domain.Page;

import app.vercel.ingenio_theta.trakr.users.dtos.CreateUserDto;
import app.vercel.ingenio_theta.trakr.users.dtos.GetUsersDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UpdateUserDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;

public interface UserService {
    Page<UserResponse> findAll(GetUsersDto query);

    UserResponse findById(String id);

    UserResponse create(CreateUserDto user);

    UserResponse update(UpdateUserDto update, String id);

    void delete(String id);
}
