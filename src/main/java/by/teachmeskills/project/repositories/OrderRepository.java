package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
