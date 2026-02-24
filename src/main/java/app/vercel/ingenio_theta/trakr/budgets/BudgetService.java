package app.vercel.ingenio_theta.trakr.budgets;

import org.springframework.data.domain.Page;

import app.vercel.ingenio_theta.trakr.budgets.dtos.BudgetResponse;
import app.vercel.ingenio_theta.trakr.budgets.dtos.CreateBudgetDto;
import app.vercel.ingenio_theta.trakr.budgets.dtos.GetBudgetsDto;
import app.vercel.ingenio_theta.trakr.budgets.dtos.UpdateBudgetDto;
import app.vercel.ingenio_theta.trakr.shared.exceptions.core.ApiException;

public interface BudgetService {
    Page<BudgetResponse> findAll(GetBudgetsDto query);

    BudgetResponse findById(String id);

    BudgetResponse create(CreateBudgetDto budget) throws ApiException;

    BudgetResponse update(String id, UpdateBudgetDto update);

    void delete(String id);
}
