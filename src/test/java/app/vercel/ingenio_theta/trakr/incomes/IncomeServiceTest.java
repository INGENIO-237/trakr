package app.vercel.ingenio_theta.trakr.incomes;

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
import app.vercel.ingenio_theta.trakr.incomes.dtos.CreateIncomeDto;
import app.vercel.ingenio_theta.trakr.incomes.dtos.IncomeResponse;
import app.vercel.ingenio_theta.trakr.incomes.dtos.GetIncomesDto;
import app.vercel.ingenio_theta.trakr.incomes.dtos.UpdateIncomeDto;
import app.vercel.ingenio_theta.trakr.incomes.models.Income;
import app.vercel.ingenio_theta.trakr.incomes.models.IncomeSource;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.ForbiddenException;
import app.vercel.ingenio_theta.trakr.shared.exceptions.common.NotFoundException;
import app.vercel.ingenio_theta.trakr.users.User;
import app.vercel.ingenio_theta.trakr.users.dtos.UserResponse;

@ExtendWith(MockitoExtension.class)
public class IncomeServiceTest {
    @Mock
    private IncomeRepository repository;
    @Mock
    private IncomeMapper mapper;
    @Mock
    private CurrentUserService currentUserService;

    @InjectMocks
    private IncomeService service;

    private String userId = obtainId();

    private User currentUser = User.builder()
            .id(userId)
            .build();

    private UserResponse userResponse = UserResponse.builder().id(currentUser.getId()).build();

    List<Income> incomes = new ArrayList<>();

    @Test
    void testCreate() {
        CreateIncomeDto dto = new CreateIncomeDto(1000, "Lorem ipsum dolor sit amet",
                IncomeSource.SALARY.toString());

        Income income = Income.builder()
                .amount(dto.getAmount())
                .description(dto.getDescription())
                .source((IncomeSource.valueOf(dto.getSource())))
                .build();

        IncomeResponse response = IncomeResponse.builder()
                .id(obtainId())
                .amount(income.getAmount())
                .description(income.getDescription())
                .source(income.getSource())
                .createdAt(LocalDateTime.now())
                .user(userResponse)
                .build();

        when(mapper.toEntity(dto)).thenReturn(income);
        when(mapper.toResponse(any())).thenReturn(response);
        when(currentUserService.getUser()).thenReturn(currentUser);
        when(repository.save(income)).thenReturn(income);

        IncomeResponse incomeResponse = service.create(dto);

        assertEquals(incomeResponse, response);
    }

    @SuppressWarnings("null")
    @Test
    void testDelete_OK() {
        String incomeId = obtainId();
        Income income = Income.builder().id(incomeId).user(currentUser).build();

        when(currentUserService.getUser()).thenReturn(currentUser);

        when(repository.findById(incomeId)).thenReturn(Optional.of(income));

        assertDoesNotThrow(() -> {
            service.delete(incomeId);
        });
    }

    @SuppressWarnings("null")
    @Test
    void testDelete_NotFound() {
        String incomeId = obtainId();

        when(repository.findById(incomeId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            service.delete(incomeId);
        });
    }

    @SuppressWarnings("null")
    @Test
    void testDelete_NonOwner() {
        String incomeId = obtainId();
        Income income = Income.builder().id(incomeId).user(currentUser).build();

        when(currentUserService.getUser()).thenReturn(User.builder().id(obtainId()).build());

        when(repository.findById(incomeId)).thenReturn(Optional.of(income));

        assertThrows(ForbiddenException.class, () -> {
            service.delete(incomeId);
        });
    }

    @SuppressWarnings({ "null", "unchecked" })
    @Test
    void testFindAll_10limit() {
        populateIncomes();
        when(mapper.toResponses(any(Page.class))).thenCallRealMethod();

        when(currentUserService.getUser()).thenReturn(currentUser);

        GetIncomesDto dto = new GetIncomesDto();

        Pageable pageable = dto.toPageable();

        Page<Income> page = new PageImpl<>(incomes, pageable, incomes.size());

        when(repository.findByUser(currentUser, pageable)).thenReturn(page);

        Page<IncomeResponse> xpenses = service.findAll(dto);

        assertNotNull(xpenses);
        assertEquals(page.getSize(), xpenses.getSize());
        assertTrue(page.getSize() == 10);
    }

    @SuppressWarnings({ "null", "unchecked" })
    @Test
    void testFindAll_getNextPage() {
        populateIncomes();
        when(mapper.toResponses(any(Page.class))).thenCallRealMethod();
        
        when(currentUserService.getUser()).thenReturn(currentUser);
        
        GetIncomesDto dto = new GetIncomesDto();
        int nextPage = dto.getPage() + 1;

        dto.setPage(nextPage);

        Pageable pageable = dto.toPageable();

        Page<Income> page = new PageImpl<>(incomes, pageable, incomes.size());

        when(repository.findByUser(currentUser, pageable)).thenReturn(page);

        Page<IncomeResponse> xpenses = service.findAll(dto);

        assertNotNull(xpenses);
        assertEquals(page.getSize(), xpenses.getSize());
        assertTrue(page.getSize() == 10);
        assertTrue(page.getNumber() == nextPage);
    }

    @SuppressWarnings({ "null", "unchecked" })
    @Test
    void testFindAll_20limit() {
        int limit = 20;

        populateIncomes();
        when(mapper.toResponses(any(Page.class))).thenCallRealMethod();

        when(currentUserService.getUser()).thenReturn(currentUser);

        GetIncomesDto dto = new GetIncomesDto();
        dto.setSize(limit);

        Pageable pageable = dto.toPageable();

        Page<Income> page = new PageImpl<>(incomes, pageable, incomes.size());

        when(repository.findByUser(currentUser, pageable)).thenReturn(page);

        Page<IncomeResponse> xpenses = service.findAll(dto);

        assertNotNull(xpenses);
        assertEquals(page.getSize(), xpenses.getSize());
        assertTrue(page.getSize() == limit);
    }

    @SuppressWarnings("null")
    @Test
    void testFindById_OK() {
        String incomeId = obtainId();
        Income income = Income.builder().id(incomeId).user(currentUser).build();

        when(currentUserService.getUser()).thenReturn(currentUser);
        when(repository.findById(incomeId)).thenReturn(Optional.of(income));
        when(mapper.toResponse(any())).thenReturn(IncomeResponse.builder().id(incomeId).user(userResponse).build());

        assertDoesNotThrow(() -> {
            service.findById(incomeId);
        }, "Income with id: " + incomeId + " not found");
    }

    @SuppressWarnings("null")
    @Test
    void testFindById_NonOwner() {
        String incomeId = obtainId();
        User owner = User.builder()
                .id(obtainId())
                .build();

        when(currentUserService.getUser()).thenReturn(currentUser);

        when(repository.findById(incomeId)).thenReturn(Optional.of(Income.builder().user(owner).build()));

        assertThrows(ForbiddenException.class, () -> {
            service.findById(incomeId);
        });
    }

    @SuppressWarnings("null")
    @Test
    void testFindById_NotFound() {
        String incomeId = obtainId();

        when(repository.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            service.findById(incomeId);
        });
    }

    @SuppressWarnings("null")
    @Test
    void testUpdate_OK() {
        String incomeId = obtainId();

        // Existing income entity to be used later for the update
        Income existingIncome = Income.builder().id(incomeId).user(currentUser).build();

        // Update body
        UpdateIncomeDto updateDto = new UpdateIncomeDto(null, null, IncomeSource.SALARY.toString());

        // Persisted income (updated version)
        Income savedIncome = Income.builder()
                .id(incomeId)
                .user(currentUser)
                .source(IncomeSource.SALARY)
                .build();

        // Income response - Updated version
        IncomeResponse xpenseResponse = IncomeResponse.builder()
                .id(incomeId)
                .user(userResponse)
                .source(IncomeSource.SALARY)
                .updatedAt(LocalDateTime.now())
                .build();

        when(repository.findById(incomeId)).thenReturn(Optional.of(existingIncome));
        when(currentUserService.getUser()).thenReturn(currentUser);
        when(repository.save(any())).thenReturn(savedIncome);
        when(mapper.toResponse(savedIncome)).thenReturn(xpenseResponse);

        IncomeResponse updatedIncome = service.update(incomeId, updateDto);

        assertEquals(xpenseResponse, updatedIncome);
    }

    @SuppressWarnings("null")
    @Test
    void testUpdate_NotFound() {
        String incomeId = obtainId();

        when(repository.findById(incomeId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            service.update(incomeId, null);
        });
    }

    @SuppressWarnings("null")
    @Test
    void testUpdate_NonOwner() {
        String incomeId = obtainId();
        Income existingIncome = Income.builder().id(incomeId).user(currentUser).build();

        when(currentUserService.getUser()).thenReturn(User.builder().id(obtainId()).build());
        when(repository.findById(incomeId)).thenReturn(Optional.of(existingIncome));

        assertThrows(ForbiddenException.class, () -> {
            service.update(incomeId, null);
        });
    }

    private String obtainId() {
        return UUID.randomUUID().toString();
    }

    @AfterEach
    private void cleanupIncomes() {
        incomes.clear();
    }

    private void populateIncomes() {
        populateIncomes(20);
    }

    private void populateIncomes(int size) {
        for (int i = 0; i < size; i++) {
            Income income = Income.builder()
                    .id(obtainId())
                    .amount(1000 + i)
                    .description("Income " + i)
                    .source(IncomeSource.SALARY)
                    .createdAt(LocalDateTime.now().minusDays(i))
                    .user(currentUser)
                    .build();
            incomes.add(income);
        }
    }
}
