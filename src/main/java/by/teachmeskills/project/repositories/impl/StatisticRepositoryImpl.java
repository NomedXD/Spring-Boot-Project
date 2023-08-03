package by.teachmeskills.project.repositories.impl;

import by.teachmeskills.project.domain.StatisticEntity;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.repositories.StatisticRepository;
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
public class StatisticRepositoryImpl implements StatisticRepository {
    @PersistenceContext
    private final EntityManager factory;
    private final static Logger logger = LoggerFactory.getLogger(StatisticRepositoryImpl.class);

    @Autowired
    public StatisticRepositoryImpl(EntityManager factory) {
        this.factory = factory;
    }

    @Override
    public StatisticEntity create(StatisticEntity entity) throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            session.persist(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while creating statistics. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
        return entity;
    }

    @Override
    public List<StatisticEntity> read() throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            return session.createQuery("from StatisticEntity", StatisticEntity.class).list();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting statistics. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public StatisticEntity update(StatisticEntity entity) throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            return session.merge(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while updating statistic. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            StatisticEntity statisticEntity = session.get(StatisticEntity.class, id);
            session.remove(statisticEntity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while deleting statistic. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public StatisticEntity getStatisticEntityById(Integer id) throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            return session.get(StatisticEntity.class, id);
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting statistic by id. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }
}