package app.vercel.ingenio_theta.trakr.budgets;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.vercel.ingenio_theta.trakr.auth.CurrentUserService;
import app.vercel.ingenio_theta.trakr.budgets.dtos.BudgetResponse;
import app.vercel.ingenio_theta.trakr.budgets.dtos.CreateBudgetDto;
import app.vercel.ingenio_theta.trakr.budgets.dtos.GetBudgetsDto;
import app.vercel.ingenio_theta.trakr.budgets.dtos.UpdateBudgetDto;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.BadRequestException;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.ForbiddenException;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.NotFoundException;
import app.vercel.ingenio_theta.trakr.shared.exceptions.core.ApiException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BudgetService implements IBudgetService {
    private final BudgetRepository repository;
    private final BudgetMapper mapper;
    private final CurrentUserService currentUserService;

    @Override
    public Page<BudgetResponse> findAll(GetBudgetsDto query) {
        Pageable pageable = query.toPageable();

        Page<Budget> budgets = repository.findByUser(currentUserService.getUser(), pageable);

        return mapper.toResponses(budgets);
    }

    @Override
    public BudgetResponse findById(String id) {
        @SuppressWarnings("null")
        Optional<Budget> buget = repository.findById(id);

        if (buget.isEmpty()) {
            throw new NotFoundException("Budget with id: " + id + " not found");
        }

        return mapper.toResponse(buget.get());
    }

    @Override
    public BudgetResponse create(CreateBudgetDto budget) throws ApiException {
        validateBudgetDates(budget.getStartDate(), budget.getEndDate());

        // TODO: Ensure there's not a budget defined for the same time interval
        Budget newBudget = mapper.toEntity(budget);

        newBudget.setUser(currentUserService.getUser());

        Budget createdBudget = repository.save(newBudget);

        return mapper.toResponse(createdBudget);
    }

    @Override
    public BudgetResponse update(String id, UpdateBudgetDto update) {
        @SuppressWarnings("null")
        Optional<Budget> existingBudget = repository.findById(id);

        if (existingBudget.isEmpty()) {
            throw new NotFoundException("Budget with id: " + id + " not found");
        }

        validateOwnership(id);

        validateBudgetDates(update.getStartDate(), update.getEndDate());

        Budget budgetToUpdate = existingBudget.get();

        mapper.updateEntity(update, budgetToUpdate);

        @SuppressWarnings("null")
        Budget updatedBudget = repository.save(budgetToUpdate);

        return mapper.toResponse(updatedBudget);
    }

    @SuppressWarnings("null")
    @Override
    public void delete(String id) {
        validateOwnership(id);
        repository.deleteById(id);
    }

    private void validateBudgetDates(LocalDate startDate, LocalDate endDate) throws ApiException {
        if (startDate.equals(endDate)) {
            throw new BadRequestException("Budget's start and end dates cannot be the same");
        }

        if (startDate.isAfter(endDate)) {
            throw new BadRequestException("Budget's start date must be before end date");
        }
    }

    private void validateOwnership(String budgetId) {
        @SuppressWarnings("null")
        Budget budget = repository.findById(budgetId).get();

        if (budget.getUser().getId() != currentUserService.getUser().getId()) {
            throw new ForbiddenException("You don't have permission to update this budget");
        }
    }

}
