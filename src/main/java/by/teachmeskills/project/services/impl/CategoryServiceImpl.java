package by.teachmeskills.project.services.impl;

import by.teachmeskills.project.domain.Category;
import by.teachmeskills.project.domain.CategoryCsv;
import by.teachmeskills.project.enums.EshopConstants;
import by.teachmeskills.project.enums.PagesPathEnum;
import by.teachmeskills.project.enums.RequestParamsEnum;
import by.teachmeskills.project.exception.CSVExportException;
import by.teachmeskills.project.exception.CSVImportException;
import by.teachmeskills.project.exception.EntityOperationException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public Category create(Category entity) throws EntityOperationException {
        return categoryRepository.create(entity);
    }

    @Override
    public List<Category> read() throws EntityOperationException {
        return categoryRepository.read();
    }

    @Override
    public Category update(Category entity) throws EntityOperationException {
        return categoryRepository.update(entity);
    }

    @Override
    public void delete(Integer id) throws EntityOperationException {
        categoryRepository.delete(id);
    }

    @Override
    public void getCategoryByName(String name) throws EntityOperationException {
        categoryRepository.getCategoryByName(name);
    }

    @Override
    public Category getCategoryById(Integer id) throws EntityOperationException {
        return categoryRepository.getCategoryById(id);
    }

    @Override
    public ModelAndView exportCategories() throws CSVExportException {
        writeCsv();
        ModelMap model = new ModelMap();
        model.addAttribute(RequestParamsEnum.CATEGORIES.getValue(), categoryRepository.read());
        model.addAttribute(RequestParamsEnum.EXPORT_IMPORT_MESSAGE.getValue(), EshopConstants.successfulExportMessage);
        return new ModelAndView(PagesPathEnum.SHOP_PAGE.getPath(), model);
    }

    private void writeCsv() throws CSVExportException {
        List<CategoryCsv> categoryCsvList = categoryCsvConverter.convertInto(categoryRepository.read());
        try (Writer categoriesWriter = Files.newBufferedWriter(Paths.get("src/main/resources/categories.csv"))) {
            StatefulBeanToCsv<CategoryCsv> categoriesSbc = new StatefulBeanToCsvBuilder<CategoryCsv>(categoriesWriter)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .build();
            categoriesSbc.write(categoryCsvList);
        } catch (IOException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException e) {
            throw new CSVExportException(EshopConstants.errorCategoriesExportMessage, PagesPathEnum.SHOP_PAGE.getPath());
        }
    }

    @Override
    public ModelAndView importCategories(MultipartFile file) throws CSVImportException {
        List<Category> categoryList = parseCsv(file);
        categoryList.forEach(category -> {
            category.setId(null);
            categoryRepository.create(category);
        });
        ModelMap model = new ModelMap();
        model.addAttribute(RequestParamsEnum.CATEGORIES.getValue(), categoryRepository.read());
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
