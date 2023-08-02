package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.exception.EntityOperationException;

public interface UserRepository extends BaseRepository<User>{
    User getUserByCredentials(String mail, String password) throws EntityOperationException;
}
