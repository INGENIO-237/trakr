package app.vercel.ingenio_theta.trakr.expenses;

import org.springframework.data.domain.Page;

import app.vercel.ingenio_theta.trakr.expenses.dtos.ExpenseResponse;
import app.vercel.ingenio_theta.trakr.expenses.dtos.CreateExpenseDto;
import app.vercel.ingenio_theta.trakr.expenses.dtos.GetExpensesDto;
import app.vercel.ingenio_theta.trakr.expenses.dtos.UpdateExpenseDto;
import app.vercel.ingenio_theta.trakr.shared.exceptions.core.ApiException;

public interface ExpenseService {
    Page<ExpenseResponse> findAll(GetExpensesDto query);

    ExpenseResponse findById(String id);

    ExpenseResponse create(CreateExpenseDto expense) throws ApiException;

    ExpenseResponse update(String id, UpdateExpenseDto update);

    void delete(String id);
}
