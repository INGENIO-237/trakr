package app.vercel.ingenio_theta.trakr.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.vercel.ingenio_theta.trakr.shared.response.ApiResponse;
import app.vercel.ingenio_theta.trakr.shared.response.PaginatedApiResponse;
import app.vercel.ingenio_theta.trakr.users.dtos.CreateUserDto;
import app.vercel.ingenio_theta.trakr.users.dtos.GetUsersDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UpdateUserDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;

@RestController
@RequestMapping("users")
public class UserController {
    @Autowired
    private IUserService service;

    @GetMapping
    public ResponseEntity<PaginatedApiResponse<UserResponse>> findAll(@ModelAttribute GetUsersDto query) {
        Page<UserResponse> users = service.findAll(query);

        PaginatedApiResponse<UserResponse> response = PaginatedApiResponse.of(users, "Users retrieved successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> findById(@PathVariable("id") String id) {
        UserResponse user = service.findById(id);

        ApiResponse<UserResponse> response = ApiResponse.of(user, "User retrieved successdully");

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> create(@Validated @RequestBody CreateUserDto user) {
        UserResponse createdUser = service.create(user);

        ApiResponse<UserResponse> response = ApiResponse.of(createdUser, "User created successfully",
                HttpStatus.CREATED);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> update(@Validated @RequestBody UpdateUserDto update,
            @PathVariable("id") String id) {
        UserResponse updatedUser = service.update(update, id);

        ApiResponse<UserResponse> response = ApiResponse.of(updatedUser, "User updated successfully");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable("id") String id) {
        service.delete(id);

        ApiResponse<Void> response = ApiResponse.of(null, "User deleted successfully");

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(response);
    }
}
