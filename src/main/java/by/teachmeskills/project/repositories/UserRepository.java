package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.exception.SQLExecutionException;

public interface UserRepository extends BaseRepository<User>{
    User getUserByCredentials(String mail, String password) throws SQLExecutionException;
}
