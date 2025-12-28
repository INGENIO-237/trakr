package app.vercel.ingenio_theta.trakr.budgets;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, String> {

}