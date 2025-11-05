package app.vercel.ingenio_theta.trakr.users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private IUserService service;

    @GetMapping
    public List<User> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Optional<User> findById(@PathVariable("id") String id) {
        return service.findById(id);
    }

    @PostMapping
    public User create(User user) {
        return service.create(user);
    }

    @PutMapping
    public User update(User user, String id) {
        return service.update(user, id);
    }

    @DeleteMapping
    public void delete(String id) {
        service.delete(id);
    }
}
