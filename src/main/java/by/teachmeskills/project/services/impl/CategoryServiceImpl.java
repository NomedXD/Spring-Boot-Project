package by.teachmeskills.project.services.impl;

import by.teachmeskills.project.domain.Category;
import by.teachmeskills.project.dto.CategoryCsv;
import by.teachmeskills.project.enums.EshopConstants;
import by.teachmeskills.project.enums.PagesPathEnum;
import by.teachmeskills.project.enums.RequestParamsEnum;
import by.teachmeskills.project.exception.CSVExportException;
import by.teachmeskills.project.exception.CSVImportException;
import by.teachmeskills.project.repositories.CategoryRepository;
import by.teachmeskills.project.services.CategoryService;
import by.teachmeskills.project.utils.CategoryCsvConverter;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryCsvConverter categoryCsvConverter;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, @Lazy CategoryCsvConverter categoryCsvConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryCsvConverter = categoryCsvConverter;
    }

    @Override
    public Category create(Category entity) {
        return categoryRepository.save(entity);
    }

    @Override
    public List<Category> read() {
        return categoryRepository.findAll();
    }

    @Override
    public Category update(Category entity) {
        return categoryRepository.save(entity);
    }

    @Override
    public void delete(Integer id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void getCategoryByName(String name) {
        categoryRepository.getCategoryByName(name);
    }

    @Override
    public Optional<Category> getCategoryById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public ModelAndView getPaginatedCategories(Integer currentPage, Integer pageSize) {
        Pageable pageable = PageRequest.of((currentPage - 1), pageSize, Sort.by("name"));
        ModelMap model = new ModelMap();
        model.addAttribute(RequestParamsEnum.CURRENT_PAGE.getValue(), currentPage);
        model.addAttribute(RequestParamsEnum.PAGE_SIZE.getValue(), pageSize);
        model.addAttribute(RequestParamsEnum.TOTAL_PAGINATED_VISIBLE_PAGES.getValue(), EshopConstants.TOTAL_PAGINATED_VISIBLE_PAGES);
        model.addAttribute(RequestParamsEnum.LAST_PAGE_NUMBER.getValue(), Math.ceil(categoryRepository.count() / pageSize.doubleValue()));
        model.addAttribute(RequestParamsEnum.CATEGORIES.getValue(), categoryRepository.findAll(pageable).getContent());
        return new ModelAndView(PagesPathEnum.SHOP_PAGE.getPath(), model);
    }

    @Override
    public void exportCategories(HttpServletResponse response) throws CSVExportException {
        writeCsv(response);
    }

    private void writeCsv(HttpServletResponse response) throws CSVExportException {
        List<CategoryCsv> categoryCsvList = categoryCsvConverter.convertInto(categoryRepository.findAll());
        try (Writer categoriesWriter = new OutputStreamWriter(response.getOutputStream())) {
            StatefulBeanToCsv<CategoryCsv> categoriesSbc = new StatefulBeanToCsvBuilder<CategoryCsv>(categoriesWriter)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=" + "categories.csv");
            categoriesSbc.write(categoryCsvList);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new CSVExportException(EshopConstants.errorCategoriesExportMessage, PagesPathEnum.SHOP_PAGE.getPath());
        }
    }

    @Override
    public ModelAndView importCategories(MultipartFile file) throws CSVImportException {
        List<Category> categoryList = parseCsv(file);
        categoryRepository.saveAll(categoryList);
        ModelMap model = new ModelMap();
        model.addAttribute(RequestParamsEnum.CATEGORIES.getValue(), categoryRepository.findAll());
        model.addAttribute(RequestParamsEnum.EXPORT_IMPORT_MESSAGE.getValue(), EshopConstants.successfulImportMessage);
        return new ModelAndView(PagesPathEnum.SHOP_PAGE.getPath(), model);
    }

    private List<Category> parseCsv(MultipartFile file) throws CSVImportException {
        if (Optional.ofNullable(file).isPresent()) {
            try (Reader categoriesReader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                CsvToBean<CategoryCsv> categoriesCtb = new CsvToBeanBuilder<CategoryCsv>(categoriesReader)
                        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                        .withType(CategoryCsv.class)
                        .withIgnoreLeadingWhiteSpace(true)
                        .build();
                List<CategoryCsv> categoryCsvList = categoriesCtb.parse();
                return categoryCsvConverter.convertFrom(categoryCsvList);
            } catch (IOException e) {
                throw new CSVImportException(EshopConstants.errorCategoriesImportMessage, PagesPathEnum.SHOP_PAGE.getPath());
            }
        } else {
            throw new CSVImportException(EshopConstants.errorFileNullMessage, PagesPathEnum.SHOP_PAGE.getPath());
        }
    }
}
