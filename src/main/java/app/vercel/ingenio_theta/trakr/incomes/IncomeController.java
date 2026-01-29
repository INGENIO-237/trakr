package app.vercel.ingenio_theta.trakr.incomes;

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

import app.vercel.ingenio_theta.trakr.incomes.dtos.CreateIncomeDto;
import app.vercel.ingenio_theta.trakr.incomes.dtos.GetIncomesDto;
import app.vercel.ingenio_theta.trakr.incomes.dtos.IncomeResponse;
import app.vercel.ingenio_theta.trakr.incomes.dtos.UpdateIncomeDto;
import app.vercel.ingenio_theta.trakr.shared.exceptions.core.ApiException;
import app.vercel.ingenio_theta.trakr.shared.response.AppApiResponse;
import app.vercel.ingenio_theta.trakr.shared.response.PaginatedApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("incomes")
@Tag(name = "Incomes", description = "Incomes API endpoints")
public class IncomeController {
    @Autowired
    private IIncomeService service;

    @GetMapping
    @Operation(summary = "Get all incomes", description = "Retrieve a paginated list of all incomes with optional filtering parameters")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Incomes retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters")
    })
    public ResponseEntity<PaginatedApiResponse<IncomeResponse>> findAll(@ModelAttribute GetIncomesDto query) {
        Page<IncomeResponse> incomes = service.findAll(query);

        PaginatedApiResponse<IncomeResponse> response = PaginatedApiResponse.of(incomes,
                "Incomes retrieved successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get income by ID", description = "Retrieve an income by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Income retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Income not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to income")

    })
    public ResponseEntity<AppApiResponse<IncomeResponse>> findById(@PathVariable("id") String id) {
        IncomeResponse Income = service.findById(id);

        AppApiResponse<IncomeResponse> response = AppApiResponse.of(Income, "Income retrieved successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create new income", description = "Create a new income with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Income created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid income data provided"),
            @ApiResponse(responseCode = "409", description = "Income already exists")
    })
    public ResponseEntity<AppApiResponse<IncomeResponse>> create(@Valid @RequestBody CreateIncomeDto Income)
            throws ApiException {
        IncomeResponse IncomeResponse = service.create(Income);

        AppApiResponse<IncomeResponse> response = AppApiResponse.of(IncomeResponse, "Income created successfully");

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update income", description = "Update an existing income by its ID with new details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Income updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid income data provided"),
            @ApiResponse(responseCode = "404", description = "Income not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to income")
    })
    public ResponseEntity<AppApiResponse<IncomeResponse>> update(@PathVariable("id") String id,
            @Valid @RequestBody UpdateIncomeDto update) {

        IncomeResponse IncomeResponse = service.update(id, update);

        AppApiResponse<IncomeResponse> response = AppApiResponse.of(IncomeResponse, "Income updated successfully");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete income", description = "Delete an income by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Income deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Income not found"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to income")
    })
    public void delete(@PathVariable("id") String id) {
        service.delete(id);
    }
}
