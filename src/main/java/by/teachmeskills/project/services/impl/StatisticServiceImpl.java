package by.teachmeskills.project.services.impl;

import by.teachmeskills.project.domain.StatisticEntity;
import by.teachmeskills.project.exception.SQLExecutionException;
import by.teachmeskills.project.repositories.StatisticRepository;
import by.teachmeskills.project.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {
    private final StatisticRepository statisticRepository;

    @Autowired
    public StatisticServiceImpl(StatisticRepository statisticRepository) {
        this.statisticRepository = statisticRepository;
    }

    @Override
    public StatisticEntity create(StatisticEntity entity) throws SQLExecutionException {
        return statisticRepository.create(entity);
    }

    @Override
    public List<StatisticEntity> read() throws SQLExecutionException {
        return statisticRepository.read();
    }

    @Override
    public StatisticEntity update(StatisticEntity entity) throws SQLExecutionException {
        return statisticRepository.update(entity);
    }

    @Override
    public void delete(int id) throws SQLExecutionException {
        statisticRepository.delete(id);
    }
}
