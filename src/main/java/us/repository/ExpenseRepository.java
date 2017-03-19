package us.repository;

import org.springframework.data.repository.CrudRepository;
import us.model.Expense;

public interface ExpenseRepository extends CrudRepository<Expense, Integer> {
}
