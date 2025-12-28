package app.vercel.ingenio_theta.trakr.users;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.vercel.ingenio_theta.trakr.shared.exceptions.common.ConflictException;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.NotFoundException;
import app.vercel.ingenio_theta.trakr.users.dtos.CreateUserDto;
import app.vercel.ingenio_theta.trakr.users.dtos.GetUsersDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UpdateUserDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private UserMapper mapper;

    @Autowired
    private PasswordEncoder encoder;

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
        Optional<User> existingUser = repository.findByEmail(user.email());

        if (existingUser.isPresent()) {
            throw new ConflictException("This email address is already taken");
        }

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

        if (update.name() != null) {
            requestedUser.setName(update.name());
        }

        if (update.email() != null) {
            requestedUser.setEmail(update.email());
        }

        if (update.password() != null) {
            requestedUser.setPassword(encoder.encode(update.password()));
        }

        @SuppressWarnings("null")
        User updatedUser = repository.save(requestedUser);

        return mapper.toResponse(updatedUser);
    }

    @Override
    public void delete(String id) {
        if (id != null) {
            repository.deleteById(id);
        }
    }

}
