package app.vercel.ingenio_theta.trakr.budgets;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.vercel.ingenio_theta.trakr.budgets.dtos.BudgetResponse;
import app.vercel.ingenio_theta.trakr.budgets.dtos.CreateBudgetDto;
import app.vercel.ingenio_theta.trakr.budgets.dtos.GetBudgetsDto;
import app.vercel.ingenio_theta.trakr.budgets.dtos.UpdateBudgetDto;
import app.vercel.ingenio_theta.trakr.shared.exceptions.core.ApiException;
import app.vercel.ingenio_theta.trakr.shared.response.AppApiResponse;
import app.vercel.ingenio_theta.trakr.shared.response.PaginatedApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("budgets")
public class BudgetController {
    private final IBudgetService service;

    @GetMapping
    public ResponseEntity<PaginatedApiResponse<BudgetResponse>> findAll(@ModelAttribute GetBudgetsDto query) {
        Page<BudgetResponse> budgets = service.findAll(query);

        PaginatedApiResponse<BudgetResponse> response = PaginatedApiResponse.of(budgets,
                "Budgets retrieved successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppApiResponse<BudgetResponse>> findById(@PathVariable("id") String id) {
        BudgetResponse budget = service.findById(id);

        AppApiResponse<BudgetResponse> response = AppApiResponse.of(budget, "Budget retrieved successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AppApiResponse<BudgetResponse>> create(@Valid @RequestBody CreateBudgetDto budget)
            throws ApiException {
        BudgetResponse budgetResponse = service.create(budget);

        AppApiResponse<BudgetResponse> response = AppApiResponse.of(budgetResponse, "Budget created successfully");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppApiResponse<BudgetResponse>> update(@PathVariable("id") String id,
            @Valid @RequestBody UpdateBudgetDto update) {

        BudgetResponse budgetResponse = service.update(id, update);

        AppApiResponse<BudgetResponse> response = AppApiResponse.of(budgetResponse, "Budget updated successfully");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        service.delete(id);
    }
}
