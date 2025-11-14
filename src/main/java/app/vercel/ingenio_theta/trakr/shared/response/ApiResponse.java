package app.vercel.ingenio_theta.trakr.shared.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> extends Response {
    private T data;

    public static <T> ApiResponse<T> of(T data, String message) {
        return ApiResponse.<T>builder()
                .data(data)
                .message(message)
                .build();
    }

    public static <T> ApiResponse<T> of(T data, String message, HttpStatus status) {
        return ApiResponse.<T>builder()
                .data(data)
                .message(message)
                .status(status)
                .build();
    }
}
