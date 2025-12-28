package app.vercel.ingenio_theta.trakr.budgets;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import app.vercel.ingenio_theta.trakr.budgets.dtos.BudgetResponse;
import app.vercel.ingenio_theta.trakr.budgets.dtos.CreateBudgetDto;

@Mapper(componentModel = "spring")
public interface BudgetMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Budget toEntity(CreateBudgetDto dto);

    BudgetResponse toResponse(Budget budget);

    default Page<BudgetResponse> toResponses(Page<Budget> page) {
        return page.map(this::toResponse);
    }
}
