package by.teachmeskills.project.repositories.impl;

import by.teachmeskills.project.domain.Order;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.repositories.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class OrderRepositoryImpl implements OrderRepository {
    @PersistenceContext
    private final EntityManager factory;
    private final static Logger logger = LoggerFactory.getLogger(OrderRepositoryImpl.class);

    @Autowired
    public OrderRepositoryImpl(EntityManager factory) {
        this.factory = factory;
    }

    @Override
    public Order create(Order entity) throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            session.persist(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while creating product. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
        return entity;
    }

    @Override
    public List<Order> read() throws EntityOperationException {
        return null;
    }

    @Override
    public Order update(Order entity) throws EntityOperationException {
        return null;
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {

    }
}
