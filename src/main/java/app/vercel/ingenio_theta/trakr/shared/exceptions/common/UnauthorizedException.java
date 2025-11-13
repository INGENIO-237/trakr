package app.vercel.ingenio_theta.trakr.shared.exceptions.common;

import org.springframework.http.HttpStatus;

import app.vercel.ingenio_theta.trakr.shared.exceptions.core.ApiException;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
