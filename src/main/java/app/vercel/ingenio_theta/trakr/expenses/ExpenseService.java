package app.vercel.ingenio_theta.trakr.expenses;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.vercel.ingenio_theta.trakr.auth.CurrentUserService;
import app.vercel.ingenio_theta.trakr.expenses.dtos.CreateExpenseDto;
import app.vercel.ingenio_theta.trakr.expenses.dtos.ExpenseResponse;
import app.vercel.ingenio_theta.trakr.expenses.dtos.GetExpensesDto;
import app.vercel.ingenio_theta.trakr.expenses.dtos.UpdateExpenseDto;
import app.vercel.ingenio_theta.trakr.expenses.models.Expense;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.ForbiddenException;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.NotFoundException;
import app.vercel.ingenio_theta.trakr.shared.exceptions.core.ApiException;

@Service
public class ExpenseService implements IExpenseService {
    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private ExpenseMapper mapper;

    @Autowired
    private CurrentUserService currentUserService;

    @Override
    public Page<ExpenseResponse> findAll(GetExpensesDto query) {
        Pageable pageable = query.toPageable();

        @SuppressWarnings("null")
        Page<Expense> expenses = repository.findAll(pageable);

        return mapper.toResponses(expenses);
    }

    @Override
    public ExpenseResponse findById(String id) {
        @SuppressWarnings("null")
        Optional<Expense> buget = repository.findById(id);

        if (buget.isEmpty()) {
            throw new NotFoundException("Expense with id: " + id + " not found");
        }

        return mapper.toResponse(buget.get());
    }

    @Override
    public ExpenseResponse create(CreateExpenseDto expense) throws ApiException {
        Expense newExpense = mapper.toEntity(expense);

        newExpense.setUser(currentUserService.getUser());

        Expense createdExpense = repository.save(newExpense);

        return mapper.toResponse(createdExpense);
    }

    @Override
    public ExpenseResponse update(String id, UpdateExpenseDto update) {
        @SuppressWarnings("null")
        Optional<Expense> existingExpense = repository.findById(id);

        if (existingExpense.isEmpty()) {
            throw new NotFoundException("Expense with id: " + id + " not found");
        }

        validateOwnership(id);

        Expense expenseToUpdate = existingExpense.get();

        mapper.updateEntity(update, expenseToUpdate);

        @SuppressWarnings("null")
        Expense updatedExpense = repository.save(expenseToUpdate);

        return mapper.toResponse(updatedExpense);
    }

    @SuppressWarnings("null")
    @Override
    public void delete(String id) {
        validateOwnership(id);
        repository.deleteById(id);
    }

    private void validateOwnership(String expenseId) {
        @SuppressWarnings("null")
        Expense expense = repository.findById(expenseId).get();

        if (!expense.getUser().getId().equals(currentUserService.getUser().getId())) {
            throw new ForbiddenException("You don't have permission to update this expense");
        }
    }

}
