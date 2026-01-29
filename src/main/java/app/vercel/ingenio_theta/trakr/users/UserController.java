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

import app.vercel.ingenio_theta.trakr.shared.response.AppApiResponse;
import app.vercel.ingenio_theta.trakr.shared.response.PaginatedApiResponse;
import app.vercel.ingenio_theta.trakr.users.dtos.CreateUserDto;
import app.vercel.ingenio_theta.trakr.users.dtos.GetUsersDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UpdateUserDto;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("users")
@Tag(name = "Users", description = "Users API endpoints")
public class UserController {
    @Autowired
    private IUserService service;

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve a paginated list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "403", description = "Forbidden access"),
    })
    public ResponseEntity<PaginatedApiResponse<UserResponse>> findAll(@ModelAttribute GetUsersDto query) {
        Page<UserResponse> users = service.findAll(query);

        PaginatedApiResponse<UserResponse> response = PaginatedApiResponse.of(users, "Users retrieved successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a single user by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to user"),
    })
    public ResponseEntity<AppApiResponse<UserResponse>> findById(@PathVariable("id") String id) {
        UserResponse user = service.findById(id);

        AppApiResponse<UserResponse> response = AppApiResponse.of(user, "User retrieved successdully");

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Create a new user with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<AppApiResponse<UserResponse>> create(@Validated @RequestBody CreateUserDto user) {
        UserResponse createdUser = service.create(user);

        AppApiResponse<UserResponse> response = AppApiResponse.of(createdUser, "User created successfully",
                HttpStatus.CREATED);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing user", description = "Update the details of an existing user by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to user"),
    })
    public ResponseEntity<AppApiResponse<UserResponse>> update(@Validated @RequestBody UpdateUserDto update,
            @PathVariable("id") String id) {
        UserResponse updatedUser = service.update(update, id);

        AppApiResponse<UserResponse> response = AppApiResponse.of(updatedUser, "User updated successfully");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @Operation(summary = "Delete a user", description = "Delete an existing user by their unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to user"),
    })
    public ResponseEntity<AppApiResponse<Void>> delete(@PathVariable("id") String id) {
        service.delete(id);

        AppApiResponse<Void> response = AppApiResponse.of(null, "User deleted successfully");

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .body(response);
    }
}
