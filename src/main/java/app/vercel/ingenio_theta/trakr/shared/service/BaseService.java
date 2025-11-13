package app.vercel.ingenio_theta.trakr.shared.service;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import app.vercel.ingenio_theta.trakr.shared.dto.PageRequestDto;

public abstract class BaseService<T, ID> {
    
    protected abstract JpaRepository<T, ID> getRepository();
    
    public Page<T> findAll(PageRequestDto pageRequest) {
        return getRepository().findAll(pageRequest.toPageable());
    }
}