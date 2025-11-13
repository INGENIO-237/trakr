package app.vercel.ingenio_theta.trakr.shared.exceptions.common;

import org.springframework.http.HttpStatus;

import app.vercel.ingenio_theta.trakr.shared.exceptions.core.ApiException;

public class ForbiddenException extends ApiException {
    public ForbiddenException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
