package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
