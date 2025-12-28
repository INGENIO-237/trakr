package app.vercel.ingenio_theta.trakr.shared.response;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaginatedApiResponse<T> extends Response {
    private List<T> data;
    private PageInfo pageInfo;

    public static <T> PaginatedApiResponse<T> of(Page<T> page, String message) {
        PageInfo pageInfo = PageInfo.builder()
                .page(page.getNumber())
                .size(page.getSize())
                .total(page.getTotalElements())
                .pages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .empty(page.isEmpty())
                .build();

        return PaginatedApiResponse.<T>builder()
                .data(page.getContent())
                .pageInfo(pageInfo)
                .message(message)
                .build();
    }
}
