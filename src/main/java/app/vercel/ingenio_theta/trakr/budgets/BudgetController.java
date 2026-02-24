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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("budgets")
@Tag(name = "Budgets", description = "Endpoints for managing budgets")
public class BudgetController {
        private final BudgetService service;

        @GetMapping
        @Operation(summary = "Get all budgets", description = "Retrieve a paginated list of all budgets with optional filtering parameters.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Budgets retrieved successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid query parameters")
        })
        public ResponseEntity<PaginatedApiResponse<BudgetResponse>> findAll(@ModelAttribute GetBudgetsDto query) {
                Page<BudgetResponse> budgets = service.findAll(query);

                PaginatedApiResponse<BudgetResponse> response = PaginatedApiResponse.of(budgets,
                                "Budgets retrieved successfully");

                return ResponseEntity.ok(response);
        }

        @GetMapping("/{id}")
        @Operation(summary = "Get budget by ID", description = "Retrieve a specific budget by its unique ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Budget retrieved successfully"),
                        @ApiResponse(responseCode = "404", description = "Budget not found"),
                        @ApiResponse(responseCode = "403", description = "Forbidden access to budget"),

        })
        public ResponseEntity<AppApiResponse<BudgetResponse>> findById(@PathVariable("id") String id) {
                BudgetResponse budget = service.findById(id);

                AppApiResponse<BudgetResponse> response = AppApiResponse.of(budget, "Budget retrieved successfully");

                return ResponseEntity.ok(response);
        }

        @PostMapping
        @Operation(summary = "Create a new budget", description = "Create a new budget with the provided details.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Budget created successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid budget data provided")
        })
        public ResponseEntity<AppApiResponse<BudgetResponse>> create(@Valid @RequestBody CreateBudgetDto budget)
                        throws ApiException {
                BudgetResponse budgetResponse = service.create(budget);

                AppApiResponse<BudgetResponse> response = AppApiResponse.of(budgetResponse,
                                "Budget created successfully");

                return ResponseEntity.ok(response);
        }

        @PutMapping("/{id}")
        @Operation(summary = "Update an existing budget", description = "Update the details of an existing budget by its ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Budget updated successfully"),
                        @ApiResponse(responseCode = "400", description = "Invalid budget data provided"),
                        @ApiResponse(responseCode = "404", description = "Budget not found"),
                        @ApiResponse(responseCode = "403", description = "Forbidden access to budget"),
        })
        public ResponseEntity<AppApiResponse<BudgetResponse>> update(@PathVariable("id") String id,
                        @Valid @RequestBody UpdateBudgetDto update) {

                BudgetResponse budgetResponse = service.update(id, update);

                AppApiResponse<BudgetResponse> response = AppApiResponse.of(budgetResponse,
                                "Budget updated successfully");

                return ResponseEntity.ok(response);
        }

        @DeleteMapping("/{id}")
        @Operation(summary = "Delete a budget", description = "Delete an existing budget by its ID.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Budget deleted successfully"),
                        @ApiResponse(responseCode = "404", description = "Budget not found"),
                        @ApiResponse(responseCode = "403", description = "Forbidden access to budget"),
        })
        public void delete(@PathVariable("id") String id) {
                service.delete(id);
        }
}
