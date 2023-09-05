package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.Order;
import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.exception.EntityOperationException;

import java.util.List;

public interface UserRepository extends BaseRepository<User>{
    User getUserById(Integer id) throws EntityOperationException;
    User getUserByCredentials(String mail, String password) throws EntityOperationException;
    List<Order> getUserOrders(Integer id) throws EntityOperationException;
}
