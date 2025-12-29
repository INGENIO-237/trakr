package app.vercel.ingenio_theta.trakr.budgets;

import org.springframework.beans.factory.annotation.Autowired;
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
import app.vercel.ingenio_theta.trakr.shared.response.ApiResponse;
import app.vercel.ingenio_theta.trakr.shared.response.PaginatedApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("budgets")
public class BudgetController {
    @Autowired
    private IBudgetService service;

    @GetMapping
    public ResponseEntity<PaginatedApiResponse<BudgetResponse>> findAll(@ModelAttribute GetBudgetsDto query) {
        Page<BudgetResponse> budgets = service.findAll(query);

        PaginatedApiResponse<BudgetResponse> response = PaginatedApiResponse.of(budgets,
                "Budgets retrieved successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BudgetResponse>> findById(@PathVariable("id") String id) {
        BudgetResponse budget = service.findById(id);

        ApiResponse<BudgetResponse> response = ApiResponse.of(budget, "Budget retrieved successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BudgetResponse>> create(@Valid @RequestBody CreateBudgetDto budget) throws ApiException {
        BudgetResponse budgetResponse = service.create(budget);

        ApiResponse<BudgetResponse> response = ApiResponse.of(budgetResponse, "Budget created successfully");

        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BudgetResponse>> update(@PathVariable("id") String id, @Valid
    @RequestBody UpdateBudgetDto update) {
        
        BudgetResponse budgetResponse = service.update(id, update);
    
        ApiResponse<BudgetResponse> response = ApiResponse.of(budgetResponse, "Budget updated successfully");
    
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        service.delete(id);
    }
}
