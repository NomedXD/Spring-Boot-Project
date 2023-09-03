package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllByCategoryId(Integer categoryId);
    List<Product> findAllByNameContainingAndDescriptionContainingOrderByName(String name, String description, Pageable pageable);
    Long countAllByNameContainingAndDescriptionContaining(String name, String description);
}
