package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.Statistic;
import by.teachmeskills.project.exception.EntityOperationException;

public interface StatisticRepository extends BaseRepository<Statistic> {
    Statistic getStatisticEntityById(Integer id) throws EntityOperationException;
}
