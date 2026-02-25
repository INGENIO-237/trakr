package app.vercel.ingenio_theta.trakr.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import app.vercel.ingenio_theta.trakr.auth.CurrentUserService;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.ConflictException;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.NotFoundException;
import app.vercel.ingenio_theta.trakr.users.dtos.CreateUserDto;
import app.vercel.ingenio_theta.trakr.users.dtos.GetUsersDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UpdateUserDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;
import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;
    @Mock
    private CurrentUserService currentUserService;
    @Mock
    private PasswordEncoder encoder;
    @InjectMocks
    private UserServiceImpl service;

    private Faker faker = new Faker();

    CreateUserDto dto = new CreateUserDto(faker.internet().emailAddress(), faker.name().name(),
            faker.internet().password());
    User user = User.builder()
            .id(faker.internet().uuidv4())
            .name(dto.name())
            .email(dto.email())
            .password(dto.password())
            .build();
    UserResponse response = UserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .createdAt(user.getCreatedAt())
            .updatedAt(user.getUpdatedAt())
            .build();

    @SuppressWarnings("null")
    @Test
    void testCreate_success() {
        when(repository.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(mapper.toEntity(dto)).thenReturn(user);
        when(mapper.toResponse(user)).thenReturn(response);

        when(repository.save(any(User.class))).thenReturn(user);

        when(encoder.encode(any(String.class))).thenReturn(faker.internet().password());

        var resp = service.create(dto);

        assertThat(resp).isNotNull();
        verify(repository).findByEmail(dto.email());
        verify(repository).save(any(User.class));
    }

    @SuppressWarnings("null")
    @Test
    void testCreate_conflict() {
        when(repository.findByEmail(dto.email())).thenReturn(Optional.of(new User()));

        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(ConflictException.class);

        verify(repository).findByEmail(dto.email());
        verify(repository, never()).save(any(User.class));
    }

    @SuppressWarnings("null")
    @Test
    void testFindById_success() {
        when(repository.findById(user.getId())).thenReturn(Optional.of(user));
        when(mapper.toResponse(user)).thenReturn(response);

        var resp = service.findById(user.getId());

        assertThat(resp).isNotNull();
        assertThat(resp.getId()).isEqualTo(user.getId());
    }

    @Test
    void testFindById_notFound() {
        String missingId = faker.internet().uuid();

        if (missingId != null) {
            when(repository.findById(missingId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> service.findById(missingId))
                    .isInstanceOf(NotFoundException.class);
        }

    }

    @SuppressWarnings({ "null", "unchecked" })
    @Test
    void testFindAll() {
        var savedUser = User.builder().name(faker.name().firstName()).email(faker.internet().emailAddress()).build();

        Page<User> pageTobeReturned = new PageImpl<User>(List.of(savedUser));

        when(repository.findAll(any(Pageable.class)))
                .thenReturn(pageTobeReturned);

        when(mapper.toResponse(any(User.class)))
                .thenReturn(UserResponse.builder().name(savedUser.getName()).email(savedUser.getEmail()).build());

        when(mapper.toResponses(any(PageImpl.class))).thenCallRealMethod();

        GetUsersDto query = new GetUsersDto();
        var page = service.findAll(query);

        assertThat(page).isNotNull();
        assertThat(page.getTotalElements()).isEqualTo(1);
        assertThat(page.getContent()).isNotNull();
        assertThat(page.getContent().get(0)).isNotNull();
    }

    @SuppressWarnings("null")
    @Test
    void testUpdate_success() {
        when(currentUserService.getUser()).thenReturn(user);

        when(repository.findById(user.getId())).thenReturn(Optional.of(user));

        UpdateUserDto update = new UpdateUserDto(faker.name().maleFirstName(), null, null);

        when(repository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        when(mapper.toResponse(any(User.class))).thenReturn(UserResponse.builder().name(update.name()).build());

        var resp = service.update(update, user.getId());

        assertThat(resp).isNotNull();
        assertThat(resp.getName()).isEqualTo(update.name());
    }

    @SuppressWarnings("null")
    @Test
    void testDelete() {
        when(currentUserService.getUser()).thenReturn(user);
        service.delete(user.getId());

        verify(repository).deleteById(user.getId());
    }
}
