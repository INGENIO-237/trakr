package app.vercel.ingenio_theta.trakr.incomes;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import app.vercel.ingenio_theta.trakr.incomes.dtos.CreateIncomeDto;
import app.vercel.ingenio_theta.trakr.incomes.dtos.IncomeResponse;
import app.vercel.ingenio_theta.trakr.incomes.dtos.UpdateIncomeDto;
import app.vercel.ingenio_theta.trakr.incomes.models.Income;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface IncomeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Income toEntity(CreateIncomeDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(UpdateIncomeDto dto, @MappingTarget Income income);

    IncomeResponse toResponse(Income income);

    default Page<IncomeResponse> toResponses(Page<Income> page) {
        return page.map(this::toResponse);
    }
}
