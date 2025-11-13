package app.vercel.ingenio_theta.trakr.users;

import org.springframework.data.domain.Page;

import app.vercel.ingenio_theta.trakr.users.dtos.GetUsersDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;

public interface IUserService {
    Page<UserResponse> findAll(GetUsersDto query);

    UserResponse findById(String id);

    UserResponse create(User user);

    UserResponse update(User user, String id);

    void delete(String id);
}
