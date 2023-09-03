package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.Order;
import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.exception.EntityOperationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByMailAndPassword(String mail, String password);
    @Query("select o from Order o where o.user.id =: userId")
    List<Order> findOrdersByUserId(@Param("userId") Integer userId) throws EntityOperationException;
}
