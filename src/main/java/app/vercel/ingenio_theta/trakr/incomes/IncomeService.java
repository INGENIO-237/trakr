package app.vercel.ingenio_theta.trakr.incomes;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.vercel.ingenio_theta.trakr.auth.CurrentUserService;
import app.vercel.ingenio_theta.trakr.incomes.dtos.CreateIncomeDto;
import app.vercel.ingenio_theta.trakr.incomes.dtos.GetIncomesDto;
import app.vercel.ingenio_theta.trakr.incomes.dtos.IncomeResponse;
import app.vercel.ingenio_theta.trakr.incomes.dtos.UpdateIncomeDto;
import app.vercel.ingenio_theta.trakr.incomes.models.Income;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.ForbiddenException;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.NotFoundException;
import app.vercel.ingenio_theta.trakr.shared.exceptions.core.ApiException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncomeService implements IIncomeService {
    private IncomeRepository repository;
    private IncomeMapper mapper;
    private CurrentUserService currentUserService;

    public IncomeService(IncomeRepository repository, IncomeMapper mapper, CurrentUserService currentUserService) {
        this.repository = repository;
        this.mapper = mapper;
        this.currentUserService = currentUserService;
    }

    @Override
    public Page<IncomeResponse> findAll(final GetIncomesDto query) {
        final Pageable pageable = query.toPageable();

        final Page<Income> incomes = repository.findByUser(currentUserService.getUser(), pageable);

        return mapper.toResponses(incomes);
    }

    @Override
    public IncomeResponse findById(final String incomeId) {
        final Income income = getValidatedExistingIncome(incomeId);

        return mapper.toResponse(income);
    }

    @Override
    public IncomeResponse create(final CreateIncomeDto income) throws ApiException {
        final Income newIncome = mapper.toEntity(income);

        newIncome.setUser(currentUserService.getUser());

        final Income createdIncome = repository.save(newIncome);

        return mapper.toResponse(createdIncome);
    }

    @Override
    public IncomeResponse update(final String incomeId, final UpdateIncomeDto update) {
        final Income existingIncome = this.getValidatedExistingIncome(incomeId);

        mapper.updateEntity(update, existingIncome);

        @SuppressWarnings("null")
        final Income updatedIncome = repository.save(existingIncome);

        return mapper.toResponse(updatedIncome);
    }

    @SuppressWarnings("null")
    @Override
    public void delete(final String incomeId) {
        this.getValidatedExistingIncome(incomeId);

        repository.deleteById(incomeId);
    }

    @SuppressWarnings("null")
    private Income getExistingIncome(final String incomeId) {
        final Optional<Income> income = repository.findById(incomeId);

        if (income.isEmpty()) {
            throw new NotFoundException("Income with id: " + incomeId + " not found");
        }

        return income.get();
    }

    private void validateOwnership(final Income income) {
        if (!income.getUser().getId().equals(currentUserService.getUser().getId())) {
            throw new ForbiddenException("You don't have permission to update this income");
        }
    }

    private Income getValidatedExistingIncome(final String incomeId) {
        final Income income = getExistingIncome(incomeId);
        validateOwnership(income);

        return income;
    }

}
