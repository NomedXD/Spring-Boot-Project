package by.teachmeskills.project.repositories.impl;

import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
    @PersistenceContext
    private final EntityManager factory;
    private final static Logger logger = LoggerFactory.getLogger(UserRepositoryImpl.class);

    @Autowired
    public UserRepositoryImpl(EntityManager factory) {
        this.factory = factory;
    }

    @Override
    public User create(User entity) throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            session.persist(entity);
        } catch (PersistenceException e) {
            if (e instanceof ConstraintViolationException) {
                return null;
            } else {
                logger.warn("SQLException while getting users. Most likely request is wrong. Full message - " + e.getMessage());
                throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
            }
        }
        return entity;
    }

    @Override
    public List<User> read() throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            return session.createQuery("from User", User.class).list();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting users. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public User update(User entity) throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            return session.merge(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting users. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            User user = session.get(User.class, id);
            session.remove(user);
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting users. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public User getUserByCredentials(String mail, String password) throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            return session.createQuery("from User u where u.mail =: mail and u.password =: password", User.class)
                    .setParameter("mail", mail).setParameter("password", password).getSingleResultOrNull();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting users. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }
}