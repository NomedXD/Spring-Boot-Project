package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.Statistic;
import by.teachmeskills.project.exception.EntityOperationException;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatisticRepository extends JpaRepository<Statistic, Integer> {
}
