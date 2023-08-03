package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.StatisticEntity;
import by.teachmeskills.project.exception.EntityOperationException;

public interface StatisticRepository extends BaseRepository<StatisticEntity> {
    StatisticEntity getStatisticEntityById(Integer id) throws EntityOperationException;
}
