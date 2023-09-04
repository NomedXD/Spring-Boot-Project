package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    void getCategoryByName(String name);
}
