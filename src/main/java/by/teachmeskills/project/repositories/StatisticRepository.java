package by.teachmeskills.project.repositories;

import by.teachmeskills.project.domain.StatisticEntity;

public interface StatisticRepository extends BaseRepository<StatisticEntity> {
    StatisticEntity getStatisticEntityById(int id);
}
