package app.vercel.ingenio_theta.trakr.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.data.domain.Page;

import app.vercel.ingenio_theta.trakr.shared.response.ApiResponse;
import app.vercel.ingenio_theta.trakr.shared.response.PaginatedApiResponse;
import app.vercel.ingenio_theta.trakr.users.dtos.GetUsersDto;
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

        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder().data(user).message("User retrieved")
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public UserResponse create(User user) {
        return service.create(user);
    }

    @PutMapping
    public UserResponse update(User user, String id) {
        return service.update(user, id);
    }

    @DeleteMapping
    public void delete(String id) {
        service.delete(id);
    }
}
