package app.vercel.ingenio_theta.trakr.users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<User> findById(String id) {
        if (id == null) {
            return Optional.empty();
        }
        return repository.findById(id);
    }

    @Override
    public User create(User user) {
        if (user == null) {
            return null;
        }
        return repository.save(user);
    }

    @Override
    public User update(User user, String id) {
        if (id == null) {
            return null;
        }
        return repository.findById(id).map(u -> {
            u.setEmail(user.getEmail());
            u.setName(user.getName());
            u.setPassword(user.getPassword());
            return repository.save(u);
        }).orElse(null);
    }

    @Override
    public void delete(String id) {
        if (id != null) {
            repository.deleteById(id);
        }
    }

}
