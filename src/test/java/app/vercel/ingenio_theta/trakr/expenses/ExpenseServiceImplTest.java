package app.vercel.ingenio_theta.trakr.expenses;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import app.vercel.ingenio_theta.trakr.auth.CurrentUserService;
import app.vercel.ingenio_theta.trakr.expenses.dtos.CreateExpenseDto;
import app.vercel.ingenio_theta.trakr.expenses.dtos.ExpenseResponse;
import app.vercel.ingenio_theta.trakr.expenses.dtos.GetExpensesDto;
import app.vercel.ingenio_theta.trakr.expenses.dtos.UpdateExpenseDto;
import app.vercel.ingenio_theta.trakr.expenses.models.Expense;
import app.vercel.ingenio_theta.trakr.expenses.models.ExpenseCategory;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.ForbiddenException;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.NotFoundException;
import app.vercel.ingenio_theta.trakr.users.User;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;
import net.datafaker.Faker;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceImplTest {
    @Mock
    private ExpenseRepository repository;
    @Mock
    private ExpenseMapper mapper;
    @Mock
    private CurrentUserService currentUserService;

    @InjectMocks
    private ExpenseServiceImpl service;

    private Faker faker = new Faker();

    private String userId = obtainId();

    private User currentUser = User.builder()
            .id(userId)
            .build();

    private UserResponse userResponse = UserResponse.builder().id(currentUser.getId()).build();

    List<Expense> expenses = new ArrayList<>();

    @Test
    void testCreate() {
        CreateExpenseDto dto = new CreateExpenseDto(1000, faker.text().text(20),
                ExpenseCategory.FOOD.toString());

        Expense expense = Expense.builder()
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .category((ExpenseCategory.valueOf(dto.getCategory())))
                .build();

        ExpenseResponse response = ExpenseResponse.builder()
                .id(obtainId())
                .amount(expense.getAmount())
                .description(expense.getDescription())
                .category(expense.getCategory())
                .createdAt(LocalDateTime.now())
                .user(userResponse)
                .build();

        when(mapper.toEntity(dto)).thenReturn(expense);
        when(mapper.toResponse(any())).thenReturn(response);
        when(currentUserService.getUser()).thenReturn(currentUser);
        when(repository.save(expense)).thenReturn(expense);

        ExpenseResponse expenseResponse = service.create(dto);

        assertEquals(expenseResponse, response);
    }

    @SuppressWarnings("null")
    @Test
    void testDelete_OK() {
        String expenseId = obtainId();
        Expense expense = Expense.builder().id(expenseId).user(currentUser).build();

        when(currentUserService.getUser()).thenReturn(currentUser);

        when(repository.findById(expenseId)).thenReturn(Optional.of(expense));

        assertDoesNotThrow(() -> {
            service.delete(expenseId);
        });
    }

    @SuppressWarnings("null")
    @Test
    void testDelete_NotFound() {
        String expenseId = obtainId();

        when(repository.findById(expenseId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            service.delete(expenseId);
        });
    }

    @SuppressWarnings("null")
    @Test
    void testDelete_NonOwner() {
        String expenseId = obtainId();
        Expense expense = Expense.builder().id(expenseId).user(currentUser).build();

        when(currentUserService.getUser()).thenReturn(User.builder().id(obtainId()).build());

        when(repository.findById(expenseId)).thenReturn(Optional.of(expense));

        assertThrows(ForbiddenException.class, () -> {
            service.delete(expenseId);
        });
    }

    @SuppressWarnings({ "null", "unchecked" })
    @Test
    void testFindAll_10limit() {
        populateExpenses();
        when(mapper.toResponses(any(Page.class))).thenCallRealMethod();

        when(currentUserService.getUser()).thenReturn(currentUser);

        GetExpensesDto dto = new GetExpensesDto();

        Pageable pageable = dto.toPageable();

        Page<Expense> page = new PageImpl<>(expenses, pageable, expenses.size());

        when(repository.findByUser(currentUser, pageable)).thenReturn(page);

        Page<ExpenseResponse> xpenses = service.findAll(dto);

        assertNotNull(xpenses);
        assertEquals(page.getSize(), xpenses.getSize());
        assertTrue(page.getSize() == 10);
    }

    @SuppressWarnings({ "null", "unchecked" })
    @Test
    void testFindAll_getNextPage() {
        populateExpenses();
        when(mapper.toResponses(any(Page.class))).thenCallRealMethod();

        when(currentUserService.getUser()).thenReturn(currentUser);

        GetExpensesDto dto = new GetExpensesDto();
        int nextPage = dto.getPage() + 1;

        dto.setPage(nextPage);

        Pageable pageable = dto.toPageable();

        Page<Expense> page = new PageImpl<>(expenses, pageable, expenses.size());

        when(repository.findByUser(currentUser, pageable)).thenReturn(page);

        Page<ExpenseResponse> xpenses = service.findAll(dto);

        assertNotNull(xpenses);
        assertEquals(page.getSize(), xpenses.getSize());
        assertTrue(page.getSize() == 10);
        assertTrue(page.getNumber() == nextPage);
    }

    @SuppressWarnings({ "null", "unchecked" })
    @Test
    void testFindAll_20limit() {
        int limit = 20;

        populateExpenses();
        when(mapper.toResponses(any(Page.class))).thenCallRealMethod();

        when(currentUserService.getUser()).thenReturn(currentUser);

        GetExpensesDto dto = new GetExpensesDto();
        dto.setSize(limit);

        Pageable pageable = dto.toPageable();

        Page<Expense> page = new PageImpl<>(expenses, pageable, expenses.size());

        when(repository.findByUser(currentUser, pageable)).thenReturn(page);

        Page<ExpenseResponse> xpenses = service.findAll(dto);

        assertNotNull(xpenses);
        assertEquals(page.getSize(), xpenses.getSize());
        assertTrue(page.getSize() == limit);
    }

    @SuppressWarnings("null")
    @Test
    void testFindById_OK() {
        String expenseId = obtainId();
        Expense expense = Expense.builder().id(expenseId).user(currentUser).build();

        when(currentUserService.getUser()).thenReturn(currentUser);
        when(repository.findById(expenseId)).thenReturn(Optional.of(expense));
        when(mapper.toResponse(any())).thenReturn(ExpenseResponse.builder().id(expenseId).user(userResponse).build());

        assertDoesNotThrow(() -> {
            service.findById(expenseId);
        }, "Expense with id: " + expenseId + " not found");
    }

    @SuppressWarnings("null")
    @Test
    void testFindById_NonOwner() {
        String expenseId = obtainId();
        User owner = User.builder()
                .id(obtainId())
                .build();

        when(currentUserService.getUser()).thenReturn(currentUser);

        when(repository.findById(expenseId)).thenReturn(Optional.of(Expense.builder().user(owner).build()));

        assertThrows(ForbiddenException.class, () -> {
            service.findById(expenseId);
        });
    }

    @SuppressWarnings("null")
    @Test
    void testFindById_NotFound() {
        String expenseId = obtainId();

        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            service.findById(expenseId);
        });
    }

    @SuppressWarnings("null")
    @Test
    void testUpdate_OK() {
        String expenseId = obtainId();

        // Existing expense entity to be used later for the update
        Expense existingExpense = Expense.builder().id(expenseId).user(currentUser).build();

        // Update body
        UpdateExpenseDto updateDto = new UpdateExpenseDto(null, null, ExpenseCategory.FOOD.toString());

        // Persisted expense (updated version)
        Expense savedExpense = Expense.builder()
                .id(expenseId)
                .user(currentUser)
                .category(ExpenseCategory.FOOD)
                .build();

        // Expense response - Updated version
        ExpenseResponse xpenseResponse = ExpenseResponse.builder()
                .id(expenseId)
                .user(userResponse)
                .category(ExpenseCategory.FOOD)
                .updatedAt(LocalDateTime.now())
                .build();

        when(repository.findById(expenseId)).thenReturn(Optional.of(existingExpense));
        when(currentUserService.getUser()).thenReturn(currentUser);
        when(repository.save(any())).thenReturn(savedExpense);
        when(mapper.toResponse(savedExpense)).thenReturn(xpenseResponse);

        ExpenseResponse updatedExpense = service.update(expenseId, updateDto);

        assertEquals(xpenseResponse, updatedExpense);
    }

    @SuppressWarnings("null")
    @Test
    void testUpdate_NotFound() {
        String expenseId = obtainId();

        when(repository.findById(expenseId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            service.update(expenseId, null);
        });
    }

    @SuppressWarnings("null")
    @Test
    void testUpdate_NonOwner() {
        String expenseId = obtainId();
        Expense existingExpense = Expense.builder().id(expenseId).user(currentUser).build();

        when(currentUserService.getUser()).thenReturn(User.builder().id(obtainId()).build());
        when(repository.findById(expenseId)).thenReturn(Optional.of(existingExpense));

        assertThrows(ForbiddenException.class, () -> {
            service.update(expenseId, null);
        });
    }

    private String obtainId() {
        return UUID.randomUUID().toString();
    }

    @AfterEach
    private void cleanupExpenses() {
        expenses.clear();
    }

    private void populateExpenses() {
        populateExpenses(20);
    }

    private void populateExpenses(int size) {
        for (int i = 0; i < size; i++) {
            Expense expense = Expense.builder()
                    .id(obtainId())
                    .amount(faker.number().randomNumber())
                    .description(faker.text().text(20))
                    .category(ExpenseCategory.FOOD)
                    .createdAt(LocalDateTime.now().minusDays(i))
                    .user(currentUser)
                    .build();
            expenses.add(expense);
        }
    }
}
