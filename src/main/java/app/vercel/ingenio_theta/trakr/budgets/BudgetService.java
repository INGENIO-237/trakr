package app.vercel.ingenio_theta.trakr.budgets;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import app.vercel.ingenio_theta.trakr.auth.CurrentUserService;
import app.vercel.ingenio_theta.trakr.budgets.dtos.BudgetResponse;
import app.vercel.ingenio_theta.trakr.budgets.dtos.CreateBudgetDto;
import app.vercel.ingenio_theta.trakr.budgets.dtos.GetBudgetsDto;
import app.vercel.ingenio_theta.trakr.budgets.dtos.UpdateBudgetDto;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.NotFoundException;

@Service
public class BudgetService implements IBudgetService {
    @Autowired
    private BudgetRepository repository;

    @Autowired
    private BudgetMapper mapper;

    @Autowired
    private CurrentUserService currentUserService;

    @Override
    public Page<BudgetResponse> findAll(GetBudgetsDto query) {
        Pageable pageable = query.toPageable();

        Page<Budget> budgets = repository.findAll(pageable);

        return mapper.toResponses(budgets);
    }

    @Override
    public BudgetResponse findById(String id) {
        Optional<Budget> buget = repository.findById(id);

        if (buget.isEmpty()) {
            throw new NotFoundException("Budget with id: " + id + " not found");
        }

        return mapper.toResponse(buget.get());
    }

    @Override
    public BudgetResponse create(CreateBudgetDto budget) {
        // TODO: Ensure there's not a budget defined for the same time interval
        Budget newBudget = mapper.toEntity(budget);

        newBudget.setUser(currentUserService.getUser());

        Budget createdBudget = repository.save(newBudget);

        return mapper.toResponse(createdBudget);
    }

    @Override
    public BudgetResponse update(String id, UpdateBudgetDto update) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }

}
