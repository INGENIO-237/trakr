package app.vercel.ingenio_theta.trakr.budgets;

import org.springframework.data.jpa.repository.JpaRepository;

import app.vercel.ingenio_theta.trakr.users.User;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

public interface BudgetRepository extends JpaRepository<Budget, String> {
    Page<Budget> findByUser(User user, Pageable pageable);
}