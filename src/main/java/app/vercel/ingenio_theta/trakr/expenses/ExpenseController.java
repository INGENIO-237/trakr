package app.vercel.ingenio_theta.trakr.expenses;

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

import app.vercel.ingenio_theta.trakr.expenses.dtos.ExpenseResponse;
import app.vercel.ingenio_theta.trakr.expenses.dtos.CreateExpenseDto;
import app.vercel.ingenio_theta.trakr.expenses.dtos.GetExpensesDto;
import app.vercel.ingenio_theta.trakr.expenses.dtos.UpdateExpenseDto;
import app.vercel.ingenio_theta.trakr.shared.exceptions.core.ApiException;
import app.vercel.ingenio_theta.trakr.shared.response.AppApiResponse;
import app.vercel.ingenio_theta.trakr.shared.response.PaginatedApiResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("expenses")
public class ExpenseController {
    @Autowired
    private IExpenseService service;

    @GetMapping
    public ResponseEntity<PaginatedApiResponse<ExpenseResponse>> findAll(@ModelAttribute GetExpensesDto query) {
        Page<ExpenseResponse> expenses = service.findAll(query);

        PaginatedApiResponse<ExpenseResponse> response = PaginatedApiResponse.of(expenses,
                "Expenses retrieved successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppApiResponse<ExpenseResponse>> findById(@PathVariable("id") String id) {
        ExpenseResponse expense = service.findById(id);

        AppApiResponse<ExpenseResponse> response = AppApiResponse.of(expense, "Expense retrieved successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<AppApiResponse<ExpenseResponse>> create(@Valid @RequestBody CreateExpenseDto expense) throws ApiException {
        ExpenseResponse expenseResponse = service.create(expense);

        AppApiResponse<ExpenseResponse> response = AppApiResponse.of(expenseResponse, "Expense created successfully");

        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<AppApiResponse<ExpenseResponse>> update(@PathVariable("id") String id, @Valid
    @RequestBody UpdateExpenseDto update) {
        
        ExpenseResponse expenseResponse = service.update(id, update);
    
        AppApiResponse<ExpenseResponse> response = AppApiResponse.of(expenseResponse, "Expense updated successfully");
    
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        service.delete(id);
    }
}
