package app.vercel.ingenio_theta.trakr.expenses;

import java.util.Optional;

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
    private ExpenseRepository repository;
    private ExpenseMapper mapper;
    private CurrentUserService currentUserService;

    public ExpenseService(
        ExpenseRepository repository,
        ExpenseMapper mapper,
        CurrentUserService currentUserService
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.currentUserService = currentUserService;
    }

    @Override
    public Page<ExpenseResponse> findAll(final GetExpensesDto query) {
        final Pageable pageable = query.toPageable();

        final Page<Expense> expenses = repository.findByUser(currentUserService.getUser(), pageable);

        return mapper.toResponses(expenses);
    }

    @Override
    public ExpenseResponse findById(final String expenseId) {
        final Expense xpense = getValidatedExistingExpense(expenseId);

        return mapper.toResponse(xpense);
    }

    @Override
    public ExpenseResponse create(final CreateExpenseDto expense) throws ApiException {
        final Expense newExpense = mapper.toEntity(expense);

        newExpense.setUser(currentUserService.getUser());

        final Expense createdExpense = repository.save(newExpense);

        return mapper.toResponse(createdExpense);
    }

    @Override
    public ExpenseResponse update(final String expenseId, final UpdateExpenseDto update) {
        final Expense existingExpense = this.getValidatedExistingExpense(expenseId);

        mapper.updateEntity(update, existingExpense);

        @SuppressWarnings("null")
        final Expense updatedExpense = repository.save(existingExpense);

        return mapper.toResponse(updatedExpense);
    }

    @SuppressWarnings("null")
    @Override
    public void delete(final String expenseId) {
        this.getValidatedExistingExpense(expenseId);

        repository.deleteById(expenseId);
    }

    @SuppressWarnings("null")
    private Expense getExistingExpense(final String expenseId) {
        final Optional<Expense> expense = repository.findById(expenseId);

        if (expense.isEmpty()) {
            throw new NotFoundException("Expense with id: " + expenseId + " not found");
        }

        return expense.get();
    }

    private void validateOwnership(final Expense expense) {
        if (!expense.getUser().getId().equals(currentUserService.getUser().getId())) {
            throw new ForbiddenException("You don't have permission to update this expense");
        }
    }

    private Expense getValidatedExistingExpense(final String expenseId) {
        final Expense expense = getExistingExpense(expenseId);
        validateOwnership(expense);

        return expense;
    }

}
