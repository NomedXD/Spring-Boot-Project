package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.User;

public interface UserRepository extends BaseRepository<User>{
    User getUserByCredentials(String mail, String password);
}
