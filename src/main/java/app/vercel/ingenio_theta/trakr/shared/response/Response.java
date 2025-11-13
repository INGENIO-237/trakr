package app.vercel.ingenio_theta.trakr.shared.response;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder(builderMethodName = "responseBuilder")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Response {
    private String message;
    @Builder.Default
    private HttpStatus status = HttpStatus.OK;
}
