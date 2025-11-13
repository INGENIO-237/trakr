package app.vercel.ingenio_theta.trakr.shared.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    private int page;
    private int size;
    private long total;
    private int pages;
    private boolean first;
    private boolean last;
}