package by.teachmeskills.project.repositories.impl;

import by.teachmeskills.project.domain.Order;
import by.teachmeskills.project.domain.Product;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
@Transactional
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
            logger.warn("SQLException while creating order. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
        return entity;
    }

    @Override
    public List<Order> read() throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            return session.createQuery("from Order", Order.class).list();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting all orders. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public Order update(Order entity) throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            return session.merge(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while updating order. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            Order order = session.get(Order.class, id);
            session.remove(order);
        } catch (PersistenceException e) {
            logger.warn("SQLException while deleting order. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }
}
