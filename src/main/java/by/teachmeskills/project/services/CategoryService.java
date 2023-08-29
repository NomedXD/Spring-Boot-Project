package by.teachmeskills.project.services;

import by.teachmeskills.project.domain.Category;
import by.teachmeskills.project.domain.User;
import by.teachmeskills.project.exception.CSVExportException;
import by.teachmeskills.project.exception.CSVImportException;
import by.teachmeskills.project.exception.EntityOperationException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

public interface CategoryService extends BaseService<Category>{
    void getCategoryByName(String name) throws EntityOperationException;

    Category getCategoryById(Integer id) throws EntityOperationException;

    ModelAndView exportCategories() throws CSVExportException;

    ModelAndView importCategories(MultipartFile file) throws CSVImportException;
}
