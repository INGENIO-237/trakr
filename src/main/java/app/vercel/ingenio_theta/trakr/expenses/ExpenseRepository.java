package app.vercel.ingenio_theta.trakr.expenses;

import org.springframework.data.jpa.repository.JpaRepository;

import app.vercel.ingenio_theta.trakr.expenses.models.Expense;

public interface ExpenseRepository extends JpaRepository<Expense, String> {

}