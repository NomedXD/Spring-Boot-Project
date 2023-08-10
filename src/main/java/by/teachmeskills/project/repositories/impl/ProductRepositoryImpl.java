package by.teachmeskills.project.repositories.impl;

import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.domain.Search;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.repositories.ProductRepository;
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
public class ProductRepositoryImpl implements ProductRepository {
    private final EntityManagerFactory factory;
    private final static Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);

    @Autowired
    public ProductRepositoryImpl(EntityManagerFactory factory) {
        this.factory = factory;
    }

    @Override
    public Product create(Product entity) throws EntityOperationException {
        try (EntityManager entityManager = factory.createEntityManager()) {
            entityManager.persist(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while creating product. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
        return entity;
    }

    @Override
    public List<Product> read() throws EntityOperationException {
        try (EntityManager entityManager = factory.createEntityManager()) {
            return entityManager.createQuery("select p from Product p", Product.class).getResultList();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting all products. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public Product update(Product entity) throws EntityOperationException {
        try (EntityManager entityManager = factory.createEntityManager()) {
            return entityManager.merge(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while updating product. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        try (EntityManager entityManager = factory.createEntityManager()) {
            Product product = entityManager.find(Product.class, id);
            entityManager.remove(product);
        } catch (PersistenceException e) {
            logger.warn("SQLException while deleting product. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    /*
        В этом методе можно читать из базы не продукт, а категорию(т е через OneToMany),
        а потом просто вернуть List, который будет в этом объекте Category. Оставил пока так
     */
    @Override
    public List<Product> getCategoryProducts(Integer categoryId) throws EntityOperationException {
        try (EntityManager entityManager = factory.createEntityManager()) {
            return entityManager.createQuery("select p from Product p where p.category.id =: categoryId", Product.class).
                    setParameter("categoryId", categoryId).getResultList();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting products by category. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public Product getProductById(Integer id) throws EntityOperationException {
        try (EntityManager entityManager = factory.createEntityManager()) {
            return entityManager.find(Product.class, id);
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting product by it's id. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public List<Product> getSearchedProducts(Search search, Integer first, Integer count) throws EntityOperationException {
        try (EntityManager entityManager = factory.createEntityManager()) {
            return entityManager.createQuery("select p from Product p where p.name like :searchString or p.description like :searchString order by p.name", Product.class).
                    setParameter("searchString","%" + search.getSearchString() + "%").setFirstResult(first).
                    setMaxResults(count).getResultList();
        } catch (PersistenceException e) {
            logger.warn("SQLException while searching products. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public Long getCountOfAllProducts() throws EntityOperationException {
        try(EntityManager entityManager = factory.createEntityManager()) {
            return entityManager.createQuery("select count(*) from Product", Long.class).getSingleResult();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting count of all products. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public Long getCountAppropriateProducts(Search search) throws EntityOperationException {
        try(EntityManager entityManager = factory.createEntityManager()) {
            return entityManager.createQuery("select count(*) from Product p where p.name like :searchString or p.description like :searchString", Long.class).setParameter("searchString","%" + search.getSearchString() + "%").getSingleResult();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting count of products. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public List<Product> readOrderedByNameInRange(Integer first, Integer count) throws EntityOperationException {
        try(EntityManager entityManager = factory.createEntityManager()) {
            return entityManager.createQuery("select p from Product p order by p.name", Product.class).setFirstResult(first).setMaxResults(count).getResultList();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting all products in name order. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }
}