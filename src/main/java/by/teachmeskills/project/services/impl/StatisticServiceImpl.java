package by.teachmeskills.project.services.impl;

import by.teachmeskills.project.domain.Statistic;
import by.teachmeskills.project.exception.EntityOperationException;
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
    public Statistic create(Statistic entity) throws EntityOperationException {
        return statisticRepository.create(entity);
    }

    @Override
    public List<Statistic> read() throws EntityOperationException {
        return statisticRepository.read();
    }

    @Override
    public Statistic update(Statistic entity) throws EntityOperationException {
        return statisticRepository.update(entity);
    }

    @Override
    public void delete(int id) throws EntityOperationException {
        statisticRepository.delete(id);
    }
}
