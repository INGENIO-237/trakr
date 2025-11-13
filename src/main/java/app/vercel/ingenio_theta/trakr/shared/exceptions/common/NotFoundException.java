package app.vercel.ingenio_theta.trakr.shared.exceptions.common;

import org.springframework.http.HttpStatus;

import app.vercel.ingenio_theta.trakr.shared.exceptions.core.ApiException;

public class NotFoundException extends ApiException {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
