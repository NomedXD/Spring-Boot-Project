package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    List<Product> findAllByCategoryId(Integer categoryId);
    List<Product> findAllByCategoryIdOrderByName(Integer categoryId, Pageable pageable);
    Long countAllByCategoryId(Integer categoryId);
}
