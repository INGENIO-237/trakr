package app.vercel.ingenio_theta.trakr.shared.dto;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import lombok.Data;

@Data
public class PageRequestDto {
    private int page = 0;
    private int size = 10;
    private String sortBy = "id";
    private String sortDir = "asc";

    public Pageable toPageable() {
        Direction direction = sortDir.equals("asc") ? Sort.Direction.fromString("asc")
                : Sort.Direction.fromString("desc");

        Sort sort = Sort.by(direction, sortBy);

        return PageRequest.of(page, size, sort);
    }
}