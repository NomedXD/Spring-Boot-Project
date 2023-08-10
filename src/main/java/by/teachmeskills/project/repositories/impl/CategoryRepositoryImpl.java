package by.teachmeskills.project.repositories.impl;

import by.teachmeskills.project.domain.Category;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.repositories.CategoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
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
public class CategoryRepositoryImpl implements CategoryRepository {
    private final EntityManagerFactory factory;
    private final static Logger logger = LoggerFactory.getLogger(CategoryRepositoryImpl.class);

    @Autowired
    public CategoryRepositoryImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public Category create(Category entity) throws EntityOperationException {
        try(EntityManager entityManager = factory.createEntityManager()) {
            entityManager.persist(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while creating category. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
        return entity;
    }

    @Override
    public List<Category> read() throws EntityOperationException {
        try (EntityManager entityManager = factory.createEntityManager()) {
            return entityManager.createQuery("select c from Category c", Category.class).getResultList();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting all categories. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public Category update(Category entity) throws EntityOperationException {
        try (EntityManager entityManager = factory.createEntityManager()) {
            return entityManager.merge(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while updating category. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        try(EntityManager entityManager = factory.createEntityManager()) {
            Category category = entityManager.find(Category.class, id);
            entityManager.remove(category);
        } catch (PersistenceException e) {
            logger.warn("SQLException while deleting category. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public Category getCategoryByName(String name) throws EntityOperationException {
        try (EntityManager entityManager = factory.createEntityManager()) {
            return entityManager.createQuery("select c from Category c where c.name =: name", Category.class).
                    setParameter("name", name).getSingleResult();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting category by it's name. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }
}
