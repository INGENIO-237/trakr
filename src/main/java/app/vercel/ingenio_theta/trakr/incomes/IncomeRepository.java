package app.vercel.ingenio_theta.trakr.incomes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import app.vercel.ingenio_theta.trakr.incomes.models.Income;
import app.vercel.ingenio_theta.trakr.users.User;

public interface IncomeRepository extends JpaRepository<Income, String> {
    Page<Income> findByUser(User user, Pageable pageable);
}
