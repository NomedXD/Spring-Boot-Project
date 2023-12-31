package by.teachmeskills.project.actuator;

import by.teachmeskills.project.domain.Statistic;
import by.teachmeskills.project.enums.PagesPathEnum;
import by.teachmeskills.project.services.CategoryService;
import by.teachmeskills.project.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.ModelAndView;

@Component
@Endpoint(id = "dbTimeInfo")
public class DatabaseTimeInfoEndpoint {
    private final CategoryService categoryService;
    private final StatisticService statisticService;
    @Autowired
    public DatabaseTimeInfoEndpoint(CategoryService categoryService, StatisticService statisticService) {
        this.categoryService = categoryService;
        this.statisticService = statisticService;
    }

    @ReadOperation
    public ModelAndView getCategoryProductStatistic(@Selector String categoryName) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        categoryService.getCategoryByName(categoryName);
        stopWatch.stop();
        ModelMap modelMap = new ModelMap();
        Statistic statistic = statisticService.create(
                new Statistic("Time to read category " + categoryName + " is " + stopWatch.getTotalTimeSeconds()));
        modelMap.addAttribute("info", statistic.getDescription());
        return new ModelAndView(PagesPathEnum.DB_TIME_INFO_PAGE.getPath(), modelMap);
    }
}
