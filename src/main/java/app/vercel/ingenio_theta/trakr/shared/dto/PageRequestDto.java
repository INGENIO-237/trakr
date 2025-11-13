package app.vercel.ingenio_theta.trakr.shared.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import lombok.Data;

@Data
public class PageRequestDto {
    private int page = 0;
    private int size = 10;
    private String sortBy = "id";
    private String sortDir = "asc";

    public Pageable toPageable() {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDir), sortBy);

        return PageRequest.of(page, size, sort);
    }
}