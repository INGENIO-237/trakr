package app.vercel.ingenio_theta.trakr.expenses;

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

import app.vercel.ingenio_theta.trakr.expenses.dtos.CreateExpenseDto;
import app.vercel.ingenio_theta.trakr.expenses.dtos.ExpenseResponse;
import app.vercel.ingenio_theta.trakr.expenses.dtos.GetExpensesDto;
import app.vercel.ingenio_theta.trakr.expenses.dtos.UpdateExpenseDto;
import app.vercel.ingenio_theta.trakr.shared.exceptions.core.ApiException;
import app.vercel.ingenio_theta.trakr.shared.response.AppApiResponse;
import app.vercel.ingenio_theta.trakr.shared.response.PaginatedApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("expenses")
@Tag(name = "Expenses", description = "Endpoints for managing expenses")
public class ExpenseController {
    private IExpenseService service;

    public ExpenseController(IExpenseService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Get all expenses", description = "Retrieve a paginated list of all expenses with optional filtering parameters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expenses retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid query parameters")
    })
    public ResponseEntity<PaginatedApiResponse<ExpenseResponse>> findAll(@ModelAttribute GetExpensesDto query) {
        Page<ExpenseResponse> expenses = service.findAll(query);

        PaginatedApiResponse<ExpenseResponse> response = PaginatedApiResponse.of(expenses,
                "Expenses retrieved successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get expense by ID", description = "Retrieve a specific expense by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Expense not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to expense"),
        })
        public ResponseEntity<AppApiResponse<ExpenseResponse>> findById(@PathVariable("id") String id) {
        ExpenseResponse expense = service.findById(id);

        AppApiResponse<ExpenseResponse> response = AppApiResponse.of(expense, "Expense retrieved successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create a new expense", description = "Create a new expense with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    public ResponseEntity<AppApiResponse<ExpenseResponse>> create(@Valid @RequestBody CreateExpenseDto expense)
            throws ApiException {
        ExpenseResponse expenseResponse = service.create(expense);

        AppApiResponse<ExpenseResponse> response = AppApiResponse.of(expenseResponse, "Expense created successfully");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing expense", description = "Update the details of an existing expense by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Expense updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Expense not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to expense")
    })
    public ResponseEntity<AppApiResponse<ExpenseResponse>> update(@PathVariable("id") String id,
            @Valid @RequestBody UpdateExpenseDto update) {

        ExpenseResponse expenseResponse = service.update(id, update);

        AppApiResponse<ExpenseResponse> response = AppApiResponse.of(expenseResponse, "Expense updated successfully");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete an expense",
            description = "Delete an existing expense by its unique ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Expense deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Expense not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to expense"),
    })
    public void delete(@PathVariable("id") String id) {
        service.delete(id);
    }
}
