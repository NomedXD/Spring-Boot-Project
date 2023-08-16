package by.teachmeskills.project.repositories.impl;

import by.teachmeskills.project.domain.Statistic;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.repositories.StatisticRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
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
    private final EntityManager entityManager;
    private final static Logger logger = LoggerFactory.getLogger(StatisticRepositoryImpl.class);

    @Autowired
    public StatisticRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Statistic create(Statistic entity) throws EntityOperationException {
        try {
            entityManager.persist(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while creating statistics. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
        return entity;
    }

    @Override
    public List<Statistic> read() throws EntityOperationException {
        try {
            return entityManager.createQuery("from Statistic", Statistic.class).getResultList();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting statistics. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public Statistic update(Statistic entity) throws EntityOperationException {
        try {
            return entityManager.merge(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while updating statistic. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        try {
            Statistic statistic = entityManager.find(Statistic.class, id);
            entityManager.remove(statistic);
        } catch (PersistenceException e) {
            logger.warn("SQLException while deleting statistic. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public Statistic getStatisticEntityById(Integer id) throws EntityOperationException {
        try {
            return entityManager.find(Statistic.class, id);
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting statistic by id. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }
}