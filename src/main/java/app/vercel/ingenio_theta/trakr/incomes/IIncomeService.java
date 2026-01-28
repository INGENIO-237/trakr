package app.vercel.ingenio_theta.trakr.incomes;

import org.springframework.data.domain.Page;

import app.vercel.ingenio_theta.trakr.incomes.dtos.IncomeResponse;
import app.vercel.ingenio_theta.trakr.incomes.dtos.CreateIncomeDto;
import app.vercel.ingenio_theta.trakr.incomes.dtos.GetIncomesDto;
import app.vercel.ingenio_theta.trakr.incomes.dtos.UpdateIncomeDto;
import app.vercel.ingenio_theta.trakr.shared.exceptions.core.ApiException;

public interface IIncomeService {
    Page<IncomeResponse> findAll(GetIncomesDto query);

    IncomeResponse findById(String id);

    IncomeResponse create(CreateIncomeDto income) throws ApiException;

    IncomeResponse update(String id, UpdateIncomeDto update);

    void delete(String id);
}
