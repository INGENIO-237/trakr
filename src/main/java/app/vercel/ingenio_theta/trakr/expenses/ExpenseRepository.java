package app.vercel.ingenio_theta.trakr.expenses;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import app.vercel.ingenio_theta.trakr.expenses.models.Expense;
import app.vercel.ingenio_theta.trakr.users.User;

public interface ExpenseRepository extends JpaRepository<Expense, String> {
    Page<Expense> findByUser(User user, Pageable pageable);
}