package app.vercel.ingenio_theta.trakr.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import app.vercel.ingenio_theta.trakr.shared.exceptions.common.NotFoundException;
import app.vercel.ingenio_theta.trakr.users.dtos.GetUsersDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;

@Service
public class UserService implements IUserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserRepository repository;
    private UserMapper mapper;

    public UserService(UserRepository repository, UserMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<UserResponse> findAll(GetUsersDto query) {
        logger.info(query.toString());
        Page<User> page = repository.findAll(query.toPageable());
        
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
    public UserResponse create(User user) {
        if (user == null) {
            return null;
        }
        User createdUser = repository.save(user);

        return mapper.toUserResponse(createdUser);
    }

    @Override
    public UserResponse update(User user, String id) {
        if (id == null) {
            return null;
        }
        User updatedUser = repository.findById(id).map(u -> {
            u.setEmail(user.getEmail());
            u.setName(user.getName());
            u.setPassword(user.getPassword());
            return repository.save(u);
        }).orElse(null);

        return mapper.toUserResponse(updatedUser);
    }

    @Override
    public void delete(String id) {
        if (id != null) {
            repository.deleteById(id);
        }
    }

}
