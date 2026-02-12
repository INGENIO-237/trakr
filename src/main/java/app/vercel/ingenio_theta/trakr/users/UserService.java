package app.vercel.ingenio_theta.trakr.users;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.vercel.ingenio_theta.trakr.auth.CurrentUserService;
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

    private CurrentUserService currentUserService;

    private PasswordEncoder encoder;

    public UserService(UserRepository repository, UserMapper mapper, CurrentUserService currentUserService, PasswordEncoder encoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.currentUserService = currentUserService;
        this.encoder = encoder;
    }

    @Override
    public Page<UserResponse> findAll(GetUsersDto query) {
        Pageable pageable = query.toPageable();

        if (pageable == null) {
            throw new IllegalArgumentException("PageRequest cannot be null");
        }

        Page<User> page = repository.findAll(pageable);

        return mapper.toResponses(page);
    }

    @Override
    public UserResponse findById(String id) {
        if (id == null) {
            return null;
        }

        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with ID '" + id + "' not found"));

        return mapper.toResponse(user);
    }

    @Override
    public UserResponse create(CreateUserDto user) {
        checkEmailAvailability(user.email());

        User newUser = mapper.toEntity(user);

        newUser.setPassword(encoder.encode(newUser.getPassword()));

        User createdUser = repository.save(newUser);

        return mapper.toResponse(createdUser);
    }

    @Override
    public UserResponse update(UpdateUserDto update, String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        User requestedUser = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("User with ID '" + id + "' not found"));

        if (requestedUser.getId() != currentUserService.getUser().getId()) {
            throw new ConflictException("You don't have permission to update this user");
        }

        if (update.email() != null) {
            checkEmailAvailability(update.email());
        }

        mapper.updateEntity(update, requestedUser);

        if (update.password() != null) {
            requestedUser.setPassword(encoder.encode(update.password()));
        }

        User updatedUser = repository.save(requestedUser);

        return mapper.toResponse(updatedUser);
    }

    @Override
    public void delete(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (id != currentUserService.getUser().getId()) {
            throw new ConflictException("You don't have permission to delete this user");
        }

        if (id != null) {
            repository.deleteById(id);
        }
    }

    private void checkEmailAvailability(String email) {
        Optional<User> existingUser = repository.findByEmail(email);

        if (existingUser.isPresent()) {
            throw new ConflictException("This email address is already taken");
        }
    }

}
