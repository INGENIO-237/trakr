package app.vercel.ingenio_theta.trakr.users;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import net.datafaker.Faker;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    private Faker faker = new Faker();

    @SuppressWarnings("null")
    @Test
    void testFindByEmail() {
        String email = faker.internet().emailAddress();
        var user = User.builder()
                .name(faker.name().name())
                .email(email)
                .password(faker.internet().password())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        var saved = repository.save(user);

        var found = repository.findByEmail(email);

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo(email);
        assertThat(saved.getId()).isNotNull();

        var missing = repository.findByEmail(faker.internet().emailAddress());
        assertThat(missing).isNotPresent();
    }
}
