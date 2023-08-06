package by.teachmeskills.project.controllers;

import by.teachmeskills.project.domain.SearchEntity;
import by.teachmeskills.project.enums.EshopConstants;
import by.teachmeskills.project.exception.EntityOperationException;
import by.teachmeskills.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/search")
@SessionAttributes({EshopConstants.SEARCH_ENTITY})
public class SearchController {
    private final ProductService productService;

    @Autowired
    public SearchController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ModelAndView getSearchPage() throws EntityOperationException {
        return productService.getSearchedProducts(null, 1);
    }

    @GetMapping("/{page}")
    /* Заметка сумасшедшего:
    required = false В SessionAttribute здесь нужна, так как при любом первом обращении к контроллеру
    searchEntity добавится в модель для маппинга из формы, но не добавляется в сессию.
    При этом метод setUpSearchEntity вызывается 1 раз всегда раньше остальных методов.
    Но после отработки любого из метода контроллера, SEARCH_ENTITY добавится в сессию,
    поэтому в методе productService.getSearchedProducts используется двойная проверка на случай,
    если мы зашли на первую страницу поиска(это первый запрос = первый метод контроллера => в сессию объект добавился и
    первая проверка теперь будет давать true, а вторая все еще false, пока не будет выполнен в первый раз
    поиск).
    Теперь идем на вторую страницу, так как полю searchField объекта SEARCH_ENTITY при этом не присваивается значение
    поиска, то мы просто идем на вторую страницу всех товаров из нашей базы данных(то есть не применяя поиск).
    После применения поиска, полю объекта будет присвоено значение, по которому будут отобраны товары.
    Чтобы отменить поиск по описанию, достаточно отправить в форме пустую строку, так как запрос в базу данных
    делается order by name и в случае поиска по названию/описанию(здесь будет %""%, то есть поиск пустой строки = все
    объекты из базы, то есть = отмена фильтра по ключевой фразе), и в случае, когда SEARCH_ENTITY был null и мы просто
    получали продукты из базы тоже order by name. -15 ч. жизни на пагинацию и поиск/фильтр с пагинацией этих данных
    (＃￣ω￣) <------ я сейчас

    Чтобы не было такой проблемы, можно передать в метод сразу Session session
     */
    public ModelAndView changeSearchPage(@SessionAttribute(name = EshopConstants.SEARCH_ENTITY , required = false) SearchEntity searchEntity, @PathVariable(name = "page") Integer currentPage) throws EntityOperationException {
        return productService.getSearchedProducts(searchEntity, currentPage);
    }

    @PostMapping
    public ModelAndView submitSearch(@ModelAttribute(EshopConstants.SEARCH_ENTITY) SearchEntity searchEntity) throws EntityOperationException {
        return productService.getSearchedProducts(searchEntity, 1);
    }

    @ModelAttribute(EshopConstants.SEARCH_ENTITY)
    public SearchEntity setUpSearchEntity() {
        return new SearchEntity();
    }
}
