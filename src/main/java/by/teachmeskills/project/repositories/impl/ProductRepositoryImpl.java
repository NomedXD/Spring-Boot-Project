package by.teachmeskills.project.repositories.impl;

import by.teachmeskills.project.domain.Product;
import by.teachmeskills.project.domain.SearchEntity;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.repositories.ProductRepository;
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
public class ProductRepositoryImpl implements ProductRepository {
    @PersistenceContext
    private final EntityManager factory;
    private final static Logger logger = LoggerFactory.getLogger(ProductRepositoryImpl.class);

    @Autowired
    public ProductRepositoryImpl(EntityManager factory) {
        this.factory = factory;
    }

    @Override
    public Product create(Product entity) throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            session.persist(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while creating product. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
        return entity;
    }

    @Override
    public List<Product> read() throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            return session.createQuery("from Product", Product.class).list();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting all products. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public Product update(Product entity) throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            return session.merge(entity);
        } catch (PersistenceException e) {
            logger.warn("SQLException while updating product. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            Product product = session.get(Product.class, id);
            session.remove(product);
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
        try (Session session = factory.unwrap(Session.class)) {
            return session.createQuery("from Product p where p.category.id =: categoryId", Product.class).
                    setParameter("categoryId", categoryId).list();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting products by category. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public Product getProductById(Integer id) throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            return session.get(Product.class, id);
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting product by it's id. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public List<Product> getSearchedProducts(SearchEntity searchEntity, Integer first, Integer count) throws EntityOperationException {
        try (Session session = factory.unwrap(Session.class)) {
            return session.createQuery("from Product p where p.name like :searchString or p.description like :searchString order by p.name", Product.class).
                    setParameter("searchString","%" + searchEntity.getSearchString() + "%").setFirstResult(first).
                    setMaxResults(count).list();
        } catch (PersistenceException e) {
            logger.warn("SQLException while searching products. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public Long getCountOfAllProducts() throws EntityOperationException {
        try(Session session = factory.unwrap(Session.class)) {
            return session.createQuery("select count(*) from Product", Long.class).getSingleResultOrNull();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting count of all products. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public Long getCountAppropriateProducts(SearchEntity searchEntity) throws EntityOperationException {
        try(Session session = factory.unwrap(Session.class)) {
            return session.createQuery("select count(*) from Product p where p.name like :searchString or p.description like :searchString", Long.class).setParameter("searchString","%" + searchEntity.getSearchString() + "%").getSingleResultOrNull();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting count of products. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }

    @Override
    public List<Product> readOrderedByNameInRange(Integer first, Integer count) throws EntityOperationException {
        try(Session session = factory.unwrap(Session.class)) {
            return session.createQuery("from Product p order by p.name", Product.class).setFirstResult(first).setMaxResults(count).list();
        } catch (PersistenceException e) {
            logger.warn("SQLException while getting all ordered products. Most likely request is wrong. Full message - " + e.getMessage());
            throw new EntityOperationException("Unexpected error on the site. How do you get here?\nCheck us later");
        }
    }
}