package app.vercel.ingenio_theta.trakr.users;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.vercel.ingenio_theta.trakr.shared.exceptions.common.ConflictException;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.NotFoundException;
import app.vercel.ingenio_theta.trakr.users.dtos.CreateUserDto;
import app.vercel.ingenio_theta.trakr.users.dtos.GetUsersDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UpdateUserDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;

@Service
public class UserService implements IUserService {
    private UserRepository repository;
    private UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<UserResponse> findAll(GetUsersDto query) {
        Pageable pageable = query.toPageable();

        if (pageable == null) {
            throw new IllegalArgumentException("PageRequest cannot be null");
        }

        Page<User> page = repository.findAll(pageable);

        return page.map(mapper::toUserResponse);
    }

    @Override
    public UserResponse findById(String id) {
        if (id == null) {
            return null;
        }

        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with ID '" + id + "' not found"));

        return mapper.toUserResponse(user);
    }

    @Override
    public UserResponse create(CreateUserDto user) {
        Optional<User> existingUser = repository.findByEmail(user.email());

        if (existingUser != null) {
            throw new ConflictException("User wiil '" + user.email() + "' already exists");
        }

        User newUser = mapper.toUser(user);

        User createdUser = repository.save(newUser);
        return mapper.toUserResponse(createdUser);
    }

    @Override
    public UserResponse update(UpdateUserDto update, String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        User requestedUser = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User with ID '" + id + "' not found"));

        if (update.name() != null) {
            requestedUser.setName(update.name());
        }

        if (update.email() != null) {
            requestedUser.setEmail(update.email());
        }

        if (update.country() != null) {
            requestedUser.setCountry(update.country());
        }

        if (update.password() != null) {
            requestedUser.setPassword(update.password());
        }

        User updatedUser = repository.save(requestedUser);

        return mapper.toUserResponse(updatedUser);
    }

    @Override
    public void delete(String id) {
        if (id != null) {
            repository.deleteById(id);
        }
    }

}
