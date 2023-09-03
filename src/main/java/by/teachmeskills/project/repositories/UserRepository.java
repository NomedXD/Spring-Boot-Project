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
    @Query("select o from Order o where o.user.id = :id")
    List<Order> findOrdersByUserId(@Param("id") Integer id) throws EntityOperationException;
    /*
        Вот здесь хорошо посмеялся. В JPQL при создании запроса через createQuery не имеет значение,
        поставишь ли ты пробел между двоеточием и id в конце o.user.id = :id, то есть можно написать так:
        select o from Order o where o.user.id =: id, что выглядит чуть более красиво. Но при таком же
        запросе в аннотации @Query, если поставить пробел после двоеточия, то получим исключение с сообщением
        Named parameter not bound: id. -1 час из-за пробела (｡◕‿◕｡)
     */
}
