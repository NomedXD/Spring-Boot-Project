package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.StatisticEntity;
import by.teachmeskills.project.exception.SQLExecutionException;

public interface StatisticRepository extends BaseRepository<StatisticEntity> {
    StatisticEntity getStatisticEntityById(int id) throws SQLExecutionException;
}
