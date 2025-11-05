package app.vercel.ingenio_theta.trakr.users;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> findAll();

    Optional<User> findById(String id);

    User create(User user);

    User update(User user, String id);

    void delete(String id);
}
