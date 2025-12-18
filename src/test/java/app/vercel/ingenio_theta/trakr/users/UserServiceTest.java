package app.vercel.ingenio_theta.trakr.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import app.vercel.ingenio_theta.trakr.shared.exceptions.common.ConflictException;
import app.vercel.ingenio_theta.trakr.users.dtos.CreateUserDto;
import app.vercel.ingenio_theta.trakr.users.dtos.GetUsersDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UpdateUserDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;
    @InjectMocks
    private UserService service;

    CreateUserDto dto = new CreateUserDto("john@example.com", "John", "secret123");
    User user = User.builder()
            .id("some-random-uuid")
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

    @Test
    void testCreate_success() {
        when(repository.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(mapper.toEntity(dto)).thenReturn(user);
        when(mapper.toResponse(user)).thenReturn(response);

        when(repository.save(any(User.class))).thenReturn(user);

        var resp = service.create(dto);

        assertThat(resp).isNotNull();
        verify(repository).findByEmail(dto.email());
        verify(repository).save(any(User.class));
    }

    @Test
    void testCreate_conflict() {
        when(repository.findByEmail(dto.email())).thenReturn(Optional.of(new User()));

        assertThatThrownBy(() -> service.create(dto))
                .isInstanceOf(ConflictException.class);

        verify(repository).findByEmail(dto.email());
        verify(repository, never()).save(any(User.class));
    }

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
        when(repository.findById("missing")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById("missing"))
                .isInstanceOf(app.vercel.ingenio_theta.trakr.shared.exceptions.common.NotFoundException.class);
    }

    @Test
    void testFindAll() {
        var user = User.builder().name("John").email("john@example.com").build();

        when(repository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(java.util.List.of(user)));

        GetUsersDto query = new GetUsersDto();
        var page = service.findAll(query);

        assertThat(page).isNotNull();
        assertThat(page.getTotalElements()).isEqualTo(1);
    }

    @Test
    void testUpdate_success() {
        var existing = User.builder().id("u1").name("John").email("john@example.com").build();

        when(repository.findById("u1")).thenReturn(Optional.of(existing));

        UpdateUserDto update = new UpdateUserDto("Johnny", null, null);

        when(repository.save(any(User.class))).thenAnswer(inv -> inv.getArgument(0));

        var resp = service.update(update, "u1");

        assertThat(resp).isNotNull();
        assertThat(resp.getName()).isEqualTo("Johnny");
    }

    @Test
    void testDelete() {
        service.delete("u1");

        verify(repository).deleteById("u1");
    }
}
