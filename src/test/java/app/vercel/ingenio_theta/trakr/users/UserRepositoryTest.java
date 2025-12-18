package app.vercel.ingenio_theta.trakr.users;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    void testFindByEmail() {
        var user = User.builder()
                .name("John Doe")
                .email("john@example.com")
                .password("secret")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        var saved = repository.save(user);

        var found = repository.findByEmail("john@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("john@example.com");
        assertThat(saved.getId()).isNotNull();

        var missing = repository.findByEmail("missing@example.com");
        assertThat(missing).isNotPresent();
    }
}
