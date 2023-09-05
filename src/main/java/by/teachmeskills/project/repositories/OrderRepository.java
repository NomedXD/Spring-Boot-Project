package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Long countAllByUserId(Integer userId);
    List<Order> findAllByUserId(Integer userId, Pageable pageable);
}
